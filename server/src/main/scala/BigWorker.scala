package server

import akka.actor.Actor
import akka.actor.Actor.{registry, actorOf}
import akka.dispatch.Future
import protocol._

class BigWorkerHandler extends Actor {
  def receive = {
    case command: DoBigWork => 
      val worker = actorOf[BigWorker].start()
      worker ! command
    case _ => //ignore other commands
  }
}

trait Work1 {
  def work1(workId: String) = {
    Thread.sleep(500)
    println("work1 finished")
    "work1"
  }
}

trait Work2 {
  def work2(workId: String, work1: String) = {
    Thread.sleep(700)
    println("work2 finished")
    "work2 based on %s".format(work1)
  }
}

trait Work3 {
  def work3(workId: String) = {
    Thread.sleep(1000)
    println("work3 finished")
    "work3"
  }
}

class BigWorker extends Actor with Work1 with Work2 with Work3 {
  def receive = {
    case DoBigWork(workId) =>
      println("Working on %s".format(workId))
      doWork(workId)
  }
  
  def doWork(workId: String) {
    //do all the work concurrently
    val f1 = Future { Work1Event(workId, work1(workId)) }
    val f2 = f1 map { e1 => Work2Event(workId, work2(workId, e1.work1)) } //work2 depends on result of work1
    val f3 = Future { Work3Event(workId, work3(workId)) }
    
    //when all of the work is finished, send each work event & the finished event to event bus
    for {
      events <- Future.sequence(List(f1, f2, f3)).await.resultOrException
      eventBus <- registry.actorFor[EventBus]
    } {
      events foreach { eventBus ! _ }
      eventBus ! BigWorkFinished(workId)
      self.stop()
    }
  }
}


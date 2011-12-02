package server

import akka.actor.Scheduler._
import java.util.concurrent.TimeUnit._
import akka.actor.Actor._
import akka.routing._
import protocol._

object Main {
  def main(args: Array[String]) {
    val host = args.headOption.getOrElse("localhost")
    val port = 2552
    
    println("Starting remote on %s:%d".format(host, port))
    remote.start(host, port)
    
    remote.register("service1", actorOf[Service1])
    
    /*val publisher = actorOf[Publisher].start()
    schedule(publisher, "tick", 1000, 1000, MILLISECONDS)
    remote.register("publisher", publisher)*/
    
    val commandBus = actorOf[CommandBus].start()
    remote.register("CommandBus", commandBus)
    
    val eventBus = actorOf[EventBus].start()
    remote.register("EventBus", eventBus)
    
    val workerHandler = actorOf[BigWorkerHandler].start()
    commandBus ! Listen(workerHandler)
    
    val eventStore = actorOf[PretendEventStore].start()
    eventBus ! Listen(eventStore)
  }
}


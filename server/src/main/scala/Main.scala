package server

import akka.actor.Scheduler._
import java.util.concurrent.TimeUnit._
import akka.actor.Actor._
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
  }
}


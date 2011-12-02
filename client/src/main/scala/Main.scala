package client

import akka.actor.Actor._
import protocol._

object Main {
  def main(args: Array[String]) {
    val (host, serverHost) = if (args.size == 2) (args(0), args(1)) else ("localhost", "localhost")
    val port = 2553
    val serverPort = 2552
    
    println("Starting remote on %s:%d".format(host, port))
    remote.start(host, port)
    
    println("Getting service1 from %s:%d".format(serverHost, serverPort))
    val service1 = remote.actorFor("service1", serverHost, serverPort)
    val client1 = actorOf[Client1].start()
    client1 ! service1
    
    /*val publisher = remote.actorFor("publisher", serverHost, serverPort)
    val subscriber = actorOf[Subscriber].start()
    subscriber ! publisher*/
  }
}


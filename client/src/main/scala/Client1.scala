package client

import protocol._
import akka.actor.{Actor, ActorRef}

class Client1 extends Actor {
  def receive = {
    case service: ActorRef => 
      service ! Numbers(List(1,2,3))
      println("Sent numbers to service")
      
      service ! Message("m1")
      println("Sent message to service")
      
      service ! ComplexMessage(Message("m2"), 1)
      println("Sent complex message to service")
      
      val ms = List(Message("m3"), Message("m4"))
      service ! Messages(ms)
      println("Sent messages to service")
      
      val cms = List(ComplexMessage(Message("m3"), 10),
                     ComplexMessage(Message("m4"), 11))
      service ! ComplexMessages(cms)
      println("Sent list of complex messages to service")
      
    case Message(msg) => println("Client1 received [%s]".format(msg))
    case ComplexMessage(Message(msg), n) => println("Client1 received [%s, %d]".format(msg, n))
    case ComplexMessages(cms) => for (cm <- cms) {
      println("Client1 received [%s, %d]".format(cm.msg.msg, cm.n))
    }
    case Numbers(ns) => println("Client1 received numbers %s".format(ns))
  }
}

package server

import protocol._
import akka.actor.Actor

class Service1 extends Actor {
  def receive = {
    case Message(msg) => 
      println("Service1 received '%s'".format(msg))
      self reply Message("Service1 processed '%s'".format(msg))
    case Messages(msgs) => println("Service1 received messages %s".format(msgs))
    case ComplexMessage(Message(msg), n) => 
      println("Service1 received '%s' with %d".format(msg, n))
      self reply ComplexMessage(Message("Service1 processed '%s'".format(msg)), n + 1)
    case ComplexMessages(cms) => for (cm <- cms) {
      println("Service1 received '%s' with %d".format(cm.msg.msg, cm.n))
      self reply ComplexMessage(Message("Service1 processed '%s'".format(cm.msg.msg)), cm.n + 1)
    }
    case Numbers(ns) =>
      println("Service1 recieved numbers %s".format(ns))
      self reply Numbers(ns.map(_ + 1))
  }
}

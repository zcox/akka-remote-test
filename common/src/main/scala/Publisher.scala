package protocol

import akka.actor.{Actor, ActorRef}

class Publisher extends Actor {
  var count = 0
  var subscribers: List[ActorRef] = Nil
  
  def receive = {
    case Subscribe => for (s <- self.sender) {
      println("Subscribing %s...".format(s))
      subscribers = subscribers :+ s
      println("subscribers = %s".format(subscribers))
    }
    case Unsubscribe => for (s <- self.sender) {
      println("Unsubscribing %s...".format(s))
      subscribers = subscribers.filterNot(_ == s)
      println("subscribers = %s".format(subscribers))
    }
    
    case "tick" => 
      count += 1
      println("Sending %d to all subscribers...".format(count))
      subscribers.foreach(_ ! count)
  }
}

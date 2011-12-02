package protocol

import akka.actor.{Actor, ActorRef}

class Subscriber extends Actor {
  def receive = {
    case publisher: ActorRef => 
      println("Subscribing to publisher...")
      publisher ! Subscribe
    case n: Int => println("Received %d".format(n))
  }
}

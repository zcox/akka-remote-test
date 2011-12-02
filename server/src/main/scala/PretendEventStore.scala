package server

import akka.actor.Actor
import protocol._

class PretendEventStore extends Actor {
  def receive = {
    case event: Event => println("Pretending to store %s".format(event))
  }
}

package server

import akka.actor.Actor
import akka.routing.Listeners
import protocol._

class CommandBus extends Actor with Listeners {
  def receive = listenerManagement orElse {
    case command: Command => gossip(command)
  }
}

class EventBus extends Actor with Listeners {
  def receive = listenerManagement orElse {
    case event: Event => gossip(event)
  }
}

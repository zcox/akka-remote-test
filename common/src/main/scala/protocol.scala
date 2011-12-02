package protocol

case class Message(msg: String)
case class Messages(msgs: List[Message])

case class ComplexMessage(msg: Message, n: Int)
case class ComplexMessages(msgs: List[ComplexMessage])

case class Numbers(numbers: List[Int])

case object Subscribe
case object Unsubscribe

sealed trait Command
case class DoBigWork(workId: String) extends Command

sealed trait Event
case class Work1Event(workId: String, work1: String) extends Event
case class Work2Event(workId: String, work2: String) extends Event
case class Work3Event(workId: String, work3: String) extends Event
case class BigWorkFinished(workId: String) extends Event


package protocol

case class Message(msg: String)
case class Messages(msgs: List[Message])

case class ComplexMessage(msg: Message, n: Int)
case class ComplexMessages(msgs: List[ComplexMessage])

case class Numbers(numbers: List[Int])

case object Subscribe
case object Unsubscribe


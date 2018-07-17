import scala.actors.Actor
import scala.actors.Actor._
val countActor =  actor {
  loop {
    react {
      case "how many ? " =>
        println("I,ve got " + mailboxSize.toString + " message in my mailbox")
    }
  }
}

countActor ! "how many ? "
countActor ! 1
countActor ! "how many ? "
//countActor ! 2
//countActor ! "how many ? "
//countActor ! 3
//countActor ! "how many ? "
//countActor ! 4
//countActor ! "how many ? "

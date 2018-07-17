import scala.actors.Actor
import scala.actors.Actor._


class Redford extends Actor {
  override def act(): Unit = {
    println("a lot of what acting is , is paying attention");
  }
}

val robert = new Redford
robert.start();


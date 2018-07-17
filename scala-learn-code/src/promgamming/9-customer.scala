import scala.actors.Actor
import scala.util.Random

case object Haircut

class Customer (val id : Int)  extends Actor {
  var shorn = false;

  override def act(): Unit = {
    loop {
      react {
        case Haircut => {
          shorn = true
          println("[c] customer " + id + " got a haircut")
        }
      }
    }
  }
}


class BarBer extends Actor {
  private val random = new Random();
def helpCustomer(customer : Customer): Unit = {
  if (this.mailboxSize >= 3) {
    println("[b] not enough seats customer " + customer.id + " away" )
  } else {
    println("[b] cutting hair of customer " + customer.id)
    Thread.sleep(100 + random.nextInt(400))
    customer ! Haircut
  }
}

  override def act(): Unit =  {
    loop {
      react {
        case customer :Customer => helpCustomer(customer)
      }
    }
  }
}

class Shop extends Actor {
  val barber = new BarBer();

  override def act(): Unit = {
    println("[s] the shop is open")
    loop {
      react {
        case customer : Customer => barber ! customer
      }
    }
  }
}
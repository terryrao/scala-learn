import scala.collection.mutable

val q = mutable.Queue(1,2,3)
val q1 = q enqueue(4);

class SlowAppendQueue[T](elems: List[T]) { // Not efficient
  def head = elems.head
  def tail = new SlowAppendQueue(elems.tail)
  def enqueue(x: T) = new SlowAppendQueue(elems ::: List(x))
}


class Cell [T] (init : T) {
  private[this] var current = init

  def get = current
  def set(x : T) = {current =x}
}

val c1 = new Cell[String]("abc")
//val c2 : Cell[Any] = c1
//c2.set(1)


class Queue[+T] (private val leading: List[T],
                 private val trailing: List[T] ) {
  def enqueue[U >: T](x: U) =
    new Queue[U](leading, x :: trailing) // ...
}

// LOWER BOUNDS
class Queue2[+T](private val leading : List[T],private val trailing : List[T]) {
  def enqueue[U >: T](x :U) = new Queue[U](leading ,x :: trailing)
}


class Fruit {
  def  name() = "fruit"
}

class Apple extends Fruit {

  override def name() = "apple"
}

class Orange extends Fruit {
  override def name(): String = "orange"
}
val list = List(new Fruit);
new Apple :: list
val a = new Queue2(list,list);
a.enqueue(new Orange);

def doesCompile(q: Queue[AnyRef]) = {};

//
//class Queue3[+T] {
//  def enqueue(x: T) = {}
//}


trait OutputChannel[-T] {
  def write(x : T);
}

trait Function1[-S,+T] {
  def apply(x : S) : T
}


class Publication(val title :String)

class Book(title : String) extends Publication(title)

object Library {
  val books : Set[Book] = Set(new Book("Programming in scala"),new Book("Walden"))

  def printBookList(info:Book => AnyRef) = {
    for (book <- books) println(info(book))
  }
}

object Customer extends App {
  def getTitle(p : Publication) : String = p.title
  Library.printBookList(getTitle);
}

class Queue4[+T] private (private[this] var leading : List[T],private[this] var trailing : List[T]) {
  private def mirror() = {
    if (leading.isEmpty) {
      while (!trailing.isEmpty) {
        leading = trailing.head :: leading
        trailing = trailing.tail
      }
    }
  }

  def head : T = {
    mirror()
    leading.head
  }

  def tail : Queue4[T] = {
    mirror()
    new Queue4(leading.tail,trailing)
  }

  def enqueue[U >: T](x:U): Unit = {
    new Queue4[U](leading,x :: trailing)
  }
}
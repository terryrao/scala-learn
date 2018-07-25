/**
  * abstract class Cat[-T, +U] {
  * def meow[W^-](volume: T^-, listener: Cat[U^+, T^-]^-)
  * : Cat[Cat[U^+, T^-]^-, U^+]^+
  * }
  *
  * @param leading
  * @param trailing
  * @tparam T
  */
class Queue[+T] private (
                          private[this] var leading: List[T],
                          private[this] var trailing: List[T]
                        ) {
  private def mirror() =
    if (leading.isEmpty) {
      while (!trailing.isEmpty) {
        leading = trailing.head :: leading
        trailing = trailing.tail
      }
    }
  def head: T = {
    mirror()
    leading.head
  }
  def tail: Queue[T] = {
    mirror()
    new Queue(leading.tail, trailing)
  }
  def enqueue[U >: T](x: U) =
    new Queue[U](leading, x :: trailing)
}


class Persion(val firstName : String, val lastName : String) extends Ordered[Persion] {
  override def compare(that: Persion): Int =  {
    val lastNameComparsion = lastName.compareToIgnoreCase(that.lastName)
    if (lastNameComparsion != 0) lastNameComparsion
    else
      firstName.compareToIgnoreCase(that.firstName)

  }

  override def toString: String = firstName + " : " + lastName;
}


def orderedMergeSort[T <: Ordered[T]](xs : List[T]) : List[T] = {
  def merge(xs : List[T],ys:List[T]) : List[T] =
    (xs,ys) match  {
      case(Nil, _ ) => ys
      case (_,Nil) => xs
      case (x :: xs1,y :: ys1) => if (x < y) x :: merge(xs1,ys) else y ::merge(xs,ys1)
    }

  val n = xs.length /2
  if (n == 0) xs
  else {
    val (ys,zs) = xs splitAt n
    merge(orderedMergeSort(ys),orderedMergeSort(zs))
  }
}



class Cell[+T](init : T) {
  private[this] var current = init
  def get = current
  def set[U >: T] (x : U) {current = x}
}

val c1 = new Cell[String]("abc")
val  c2 : Cell[AnyRef] = c1;


class Compare2[T<:Comparable[T]](val first:T,val second:T ){
  def compare=if(first.compareTo(second)>0) 1 else 0
}

class LowerBounds[+T](val first:T) {
  def replace[R >: T](newFirst:R)=new LowerBounds[R](newFirst)
}

class Person2[-A]{ def test(x:A){} }

class Person3[+A]{ def test[U >: A](x:U){} }


class Person4[+A] {
  def test : A = null.asInstanceOf[A]
}

class Person5[-A] {
  def test [U <:A](x : U) = {}
}

class Person(val name:String){
  override def toString()=name
}

class Student(name:String) extends Person(name)
class Teacher(name:String) extends Person(name)

class Pair[T](val first:T,val second:T){
  override def toString()="first:"+first+"    second: "+second;
}

object TypeWildcard extends App {
  //Pair的类型参数限定为[_<:Person]，即输入的类为Person及其子类
  //类型通配符和一般的泛型定义不一样，泛型在类定义时使用，而类型能配符号在使用类时使用
  def makeFriends(p:Pair[_<:Person])={
    println(p.first +" is making friend with "+ p.second)
  }
  makeFriends(new Pair(new Student("john"),new Teacher("摇摆少年梦")))
}
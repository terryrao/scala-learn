class Food
abstract class Animal {
  type SuitableFood <: Food
  def eat(food : SuitableFood)
}

class Grass extends Food
class Cow extends Animal {
  type SuitableFood = Grass
  override def eat(food: Grass): Unit = println("eating grass")
}

/*
class Fish extends Food
val bessy: Animal = new Cow

bessy eat new bessy.SuitableFood
*/

class DogFood extends Food
class Dog extends Animal {
  type SuitableFood = DogFood
  override def eat(food: DogFood) = println("dog eat dogFood")
}

val bessy = new Cow

val lassie = new Dog
val bootsie = new Dog

lassie eat (new bootsie.SuitableFood)


//枚举类

object Color extends Enumeration {
  val red = Value
  val green = Value
  val blue = Value
}

var list = List(1,2,3)
list :: List(12,12)


def glob(x:Any):Any= x match {
  case   1 | "1" | "one"  => "one "
  case "two"=> 2
  case s:String => "String"
  case y:Int=>"Int 类型 "
  case _ => "其他"
}

println(glob(4))



abstract class Expr
case class Var(name: String) extends Expr
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String,
                 left: Expr, right: Expr) extends Expr


def simplifyTop(expr:Expr):Expr = expr match {
  case UnOp("-",UnOp("-",e)) => e
  case BinOp("+",e,Number(0)) => e
  case BinOp("*",e,Number(1)) => e
  case _ => expr
}

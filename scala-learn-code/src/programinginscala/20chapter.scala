


abstract class Fruit {
  val v : String
  def m :String
}

abstract class Apple extends Fruit {
  val v : String
  val m : String
}

abstract class BadApple extends  Fruit {
  def v : String // error
  def m :String
}

trait AbstractTime {
  def hour: Int // getter for `hour'
  def hour_=(x: Int) // setter for `hour'
  def minute: Int // getter for `minute'
  def minute_=(x: Int) // setter for `minute'
}
val x = 1
trait RationalTrait {
  val numerArg: Int
  val denomArg: Int
  require(denomArg != 0)
  private val g = gcd(numerArg, denomArg)
  val numer = numerArg / g
  val denom = denomArg / g
  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)
  override def toString = numer + "/" + denom
}

/**
  * java.lang.IllegalArgumentException: requirement failed
  * at scala.Predef$.require(Predef.scala:207)
  * at RationalTrait$class.$init$(<console>:10)
  * ... 28 elided
  */

// 这个实例说明类参数和抽象属性初始化顺序不一致。 类参数在它传入类构造器时就已经计算(除非这个参数是按名称传递的)
// 在子类实现的 val 变量相反，它会在父类初始化后再计算

//预初始化实例
new {
  val numerArg = 1 * x
  val denomArg = 2 * x
} with RationalTrait


//在一个 object 或者 类 中定义中预初始化

object twoThirds extends  {
  val numerArg = 2
  val denumArg = 3
} with RationalTrait {
  override val denomArg = 12
}

class RationalClass(n : Int,d:Int) extends {
  val numerArg = n
  val denomArg = d
} with RationalTrait {
    def + (that : RationalClass) = new RationalClass(
      numer * that.denom + that.denom* denom,denom* that.denom
    )
}

// lazy val 被  lazy 修饰的值只有再第一次使用的时候才会初始化
//不同于 无参方法， lazy val 只能计算一次
object Demo {
  val x = {println("initializing x "); "done"}
}

object Demo2 {
  lazy val x = {println("initializing x "); "done"}
}

trait lazyRationalTrait {
  val numerArg : Int
  val denomArg : Int
  lazy val numer = numerArg / g
  lazy val denom = denomArg /g

  override def toString: String = numer + "/" + denom

  private lazy val g = {
    require(denomArg != 0)
    gcd(numerArg,denomArg)
  }

  private def gcd(a : Int,b : Int) : Int = if (b == 0) a else gcd(b, a % b)

}


val x2 = 2
new lazyRationalTrait {
  override val numerArg: Int = 1 * x2
  override val denomArg: Int = 2 * x2
}

/**
  * lazy 初始化顺序
1. A fresh instance of LazyRationalTrait gets created and the initialization code
ofLazyRationalTrait is run. This initialization code is empty; none of the fields
ofLazyRationalTrait is initialized yet.
2. Next, the primary constructor of the anonymous subclass defined by the new expression is
executed. This involves the initialization of numerArg with 2 and denomArg with 4.
3. Next, the toString method is invoked on the constructed object by the interpreter, so that the
resulting value can be printed.
4. Next, the numer field is accessed for the first time by the toString method in
traitLazyRationalTrait, so its initializer is evaluated.
5. The initializer of numer accesses the private field, g, so g is evaluated next. This evaluation
accesses numerArg and denomArg, which were defined in Step 2.
6. Next, the toString method accesses the value of denom, which causes denom's evaluation. The
evaluation of denom accesses the values of denomArg and g. The initializer of the g field is not
re-evaluated, because it was already evaluated in Step 5.
7. Finally, the result string "1/2" is constructed and printed.
  */


// 抽象类型

/*class Food
abstract  class Animal {
  def eat(food : Food)
}

class Grass extends Food
class Cow extends Animal {
    // def eat(food : Grass) = {} this won't compile

}*/
class Food
abstract class Animal {
  type SuitableFood <: Food
  def eat(food : SuitableFood)
}


class Grass extends Food
class Cow extends Animal {
  override type SuitableFood = Grass

  override def eat(food: Grass): Unit = println("eating grass")
}
class Fish extends Food
val bessy: Animal = new Cow

bessy eat new bessy.SuitableFood
//bessy eat (new Grass)

class DogFood extends Food
class Dog extends Animal {
  type SuitableFood = DogFood
  override def eat(food: DogFood) = {}
}

val lassie = new Dog
//lassie eat (new bessy.SuitableFood) error
val bootsie = new Dog
lassie eat new bootsie.SuitableFood
// 路径依赖类型


//结构化子类型

abstract class CurrencyZone {
  type Currency <: AbstractCurrency
  def make(x: Long): Currency
  abstract class AbstractCurrency {
    val amount: Long
    def designation: String
    def + (that: Currency): Currency =
      make(this.amount + that.amount)
    def * (x: Double): Currency =
      make((this.amount * x).toLong)
    def - (that: Currency): Currency =
      make(this.amount - that.amount)
    def / (that: Double) =
      make((this.amount / that).toLong)
    def / (that: Currency) =
      this.amount.toDouble / that.amount
    def from(other: CurrencyZone#AbstractCurrency): Currency =
      make(math.round(
        other.amount.toDouble * Converter.exchangeRate(other.designation)(this.designation)))
    private def decimals(n: Long): Int =
      if (n == 1) 0 else 1 + decimals(n / 10)
    override def toString =
      ((amount.toDouble / CurrencyUnit.amount.toDouble)
        formatted ("%." + decimals(CurrencyUnit.amount) + "f")
        + " " + designation)
  }
  val CurrencyUnit: Currency
}


object US extends CurrencyZone {
  abstract class Dollar extends AbstractCurrency {
    def designation = "USD"
  }
  type Currency = Dollar
  def make(cents: Long) = new Dollar {
    val amount = cents
  }

  val Cent = make(1)
  val Dollar = make(100)
  val CurrencyUnit = Dollar
}

object Europe extends CurrencyZone {
  abstract class Euro extends AbstractCurrency {
    def designation = "EUR"
  }
  type Currency = Euro
  def make(cents: Long) = new Euro {
    val amount = cents
  }
  val Cent = make(1)
  val Euro = make(100)
  val CurrencyUnit = Euro
}
object Japan extends CurrencyZone {
  abstract class Yen extends AbstractCurrency {
    def designation = "JPY"
  }
  type Currency = Yen
  def make(yen: Long) = new Yen {
    val amount = yen
  }
  val Yen = make(1)
  val CurrencyUnit = Yen
}

object Converter {
  var exchangeRate = Map(
    "USD" -> Map("USD" -> 1.0 , "EUR" -> 0.7596,
      "JPY" -> 1.211 , "CHF" -> 1.223),
    "EUR" -> Map("USD" -> 1.316 , "EUR" -> 1.0 ,
      "JPY" -> 1.594 , "CHF" -> 1.623),
    "JPY" -> Map("USD" -> 0.8257, "EUR" -> 0.6272,
      "JPY" -> 1.0 , "CHF" -> 1.018),
    "CHF" -> Map("USD" -> 0.8108, "EUR" -> 0.6160,
      "JPY" -> 0.982 , "CHF" -> 1.0 )
  )
}


Japan.Yen from US.Dollar * 100
Japan.Yen from US.Dollar * 100




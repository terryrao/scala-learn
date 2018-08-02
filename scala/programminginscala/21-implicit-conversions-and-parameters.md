# Implicit Conversions And Parameters
## 隐式类(Implicit class)
形式：
```scala
    case class Rectangle(width: Int,height : Int)
    implicit class RectangleMaker(width : Int) {
        def x(height : Int) = Rectangle(width,height)
        // Automatically generated
        // implicit def RectangleMaker(width: Int) =new RectangleMaker(width)
    }
    val myRectangle = 3 x 4
  // myRectangle: Rectangle = Rectangle(3,4)
```
限制：隐式类型不能是 `case class` ,并且构造器有且只有一个参数

## 隐式参数 
形式：
```scala
class PreferredPrompt(val preference: String)

object Greeter{
  def greet(name : String) (implicit prompt: PreferredPrompt) = {
    println("Welcome, " + name + " . the System is ready. ")
    println(prompt.preference)
  }
}

implicit val bobPrompt = new PreferredPrompt("relax > ")

Greeter.greet("Bob")/*(bobPrompt)*/
```
note : `implicit` 只能于整个参数列表，不能用于单个参数





import java.awt.event.{ActionEvent, ActionListener}

/***
  * Implicit definitions are those that the compiler is allowed to insert into a program in order to fix any of
  * its type errors. For example, if x + y does not type check, then the compiler might change it
  * to convert(x) + y, where convert is some available implicit conversion.
  */

import javax.swing.JButton

// pre java 8 style
val button = new JButton
button.addActionListener(
  new ActionListener {
    def actionPerformed(event: ActionEvent) = {
      println("pressed!")
    }
  }
)

implicit def function2ActionListener(f: ActionEvent => Unit) =
  new ActionListener {
    def actionPerformed(event: ActionEvent) = f(event)
  }

new JButton
button.addActionListener(
  (_: ActionEvent) => println("pressed!")
)
// scala style
button.addActionListener(
  (_: ActionEvent) => println("pressed!")
)

// 隐式转换
implicit def doubleToInt(x : Double): Int = x.toInt
val i:Int = 3.5

//接受者转换 类开转换

case class Rectangle(width: Int, height: Int)
//An implicit class cannot be a case class, and its constructor must have exactly one
//parameter. Also, an implicit class must be located within some other object, class, or trait
implicit class RectangleMaker(width: Int) {
  def x(height: Int) = Rectangle(width, height)
// automatically generated
//  implicit def RectangleMaker(width: Int) = new RectangleMaker(width)
}



val myRectangle = 3 x 4






case class Point(x:Double,y:Double)

abstract class Shape {
  def draw():Unit
}

case class Circle(center:Point,radius:Double) extends Shape {
  override def draw(): Unit = println("draw circle : " + this)
}

case class Rectangle(lowerLeft : Point, height :Double,width : Double) extends  Shape {
  override def draw(): Unit = println("draw rectangle : " + this)
}


case class Triangle(point1 : Point,point2: Point,point3 : Point)extends Shape {
  override def draw() : Unit = println("draw triangle : " + this)
}

val shapesList = List(Circle(Point(0.0, 0.0), 1.0),Circle(Point(5.0, 2.0), 3.0),Rectangle(Point(0.0, 0.0), 2, 5),Rectangle(Point(-2.0, -1.0), 4, 3),
  Triangle(Point(0.0, 0.0), Point(1.0, 0.0), Point(0.0, 1.0)))

val shape1 = shapesList.head

println("shape1 " + shape1 + "; shape1.hashCode : " + shape1.hashCode())

for (shape2 <- shapesList) {
  println("shape2: "+shape2+". 1 == 2 ? "+(shape1 == shape2))
}


val circle1 = Circle(Point(0.0,0.0),2.0)
val circle2 = circle1 copy (radius = 4)
println(circle1)
println(circle2)

ArrowAssoc()
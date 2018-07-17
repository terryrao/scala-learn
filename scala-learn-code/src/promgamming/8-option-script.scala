val someNumber = Some(5)
val noneNumber = None

for (option <- List(someNumber,noneNumber)) {
  option.foreach(n => println(n*5))
}

println(List(1,2,3,4,5,6) reduceLeft(_ + _))
println(List(1,2,3,4,5,6).foldLeft(0)(_ * _))

println(List(1, 2, 3, 4, 5, 6).foldLeft(List[String]()) {
  (list, x) => ("<" + x + ">") :: list
}.reverse)

//println("aa")
println(List(1, 2, 3, 4, 5, 6).foldLeft(1)(_ + _))
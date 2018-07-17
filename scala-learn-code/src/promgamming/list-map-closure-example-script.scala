var factor = 3
val multiplier = (i :Int) => i * factor
val l1 = List(1,2,3,4,5) map multiplier
factor = 5
val l2 = List(1,2,3,4,5) map multiplier;
println(l1)
println(l2)


println(List(1, 2, 3, 4, 5, 6).foldLeft(List[String]()) {
  (list, x) => ("<" + x + ">") :: list
}.reverse)

println(List(1,2,3,4,5,6).foldLeft(10)(_ * _))
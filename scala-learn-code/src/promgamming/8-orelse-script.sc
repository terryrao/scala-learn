val a : PartialFunction[Boolean,String] = {case true => "truthful"}
val b : PartialFunction[Boolean,String] = {case x => "sketchy"}
val tester = a orElse b
println(tester (1 == 1))
println(tester(2 + 2 == 5))


def flatten(list: List[_]) : List[_] = list flatMap {
  case head :: tail => head :: flatten(tail)
  case Nil => Nil
  case x => List(x)
}
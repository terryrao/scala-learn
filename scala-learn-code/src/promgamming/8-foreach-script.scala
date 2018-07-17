
List(1,2,3,4,5) foreach {
  i => println("Int : " + i)
}

val stateCapitals = Map(
  "Alabama" -> "Montgomery",
  "Alaska" ->"Juneau",
  "Wyoming" ->"Cheyenne"
)

stateCapitals foreach {
  kv => println(kv._1 + " : " +kv._2)
}

val map2 = stateCapitals map { kv => (kv._1,kv._2.length)}

val lengths = Map[String,Int]() ++ map2

//println(lengths)

//println(map2)

val graph = List("a",List("b1","b2","b3"),List("c1",Nil,"c2"),Nil,"e")

def flatten(list: List[_]) : List[_] = list flatMap {
  case head :: tail => head::flatten(tail)
  case Nil => Nil
  case x => List(x)
}

//println(flatten(graph))


println(List(1,2,3,4,5) map { _ * 2})

println(List(1,2,3,4,5) reduceLeft {_ * _})
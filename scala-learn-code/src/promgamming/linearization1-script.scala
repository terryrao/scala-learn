class C1{
  def m = List("C1")
}

trait T1 extends C1 {
  override def m: List[String] = {"T1"::super.m}
}

trait T2 extends C1 {
  override def m: List[String] = {"T2"::super.m}
}

trait T3 extends C1 {
  override def m: List[String] = {"T3"::super.m}
}

class C2 extends T1 with T2 with T3 {
  override def m: List[String] = "C2" :: super.m
}

val c2 = new C2
val  l = List(1)
println(2 :: l)

println(c2.m)
## Functional Programming 
### curry 柯里化
形式：
    
    1. cat(s1: String) = (s2 : String) => s1 + s2
    2. def cat(s1: String,s2 : String) = s1 + s2
    val curryCat = Function.curried(cat _)
    
### implicit conversions
形式：
```scala
    implicit def stringWrapper(x: String) = new runtime.RichString(x)
    
    def multiplier(i: Int)(implicit factor: Int) {
    println(i * factor)
    }
```
    
### Call by Name ,Call by Value

call by Name :
按名称传递的参数是通过省略正常参数为函数的括号来指定的：
```scala
    def myCallByNameFunction(callByNameParameter: => ReturnType)
    //或者
    def myCallByNameFunction(callByNameParameter: () => ReturnType)
```

```scala
    def whileAwesome(conditional : => Boolean) (f : => Unit): Unit = {
       if(conditional) {
        f 
        whileAwesome(conditional)(f)
       }
    }
    
    var cont = 0
    whileAwesome(cont < 5) {
      println("still awesome")
      cont +=1
    }
```
如果去掉 `=>` 则变成了无限循环

### Lazy Vals
```scala
trait AbstractT2 {
println("In AbstractT2:")
val value: Int
lazy val inverse = { println("initializing inverse:"); 1.0/value }
//println("AbstractT2: value = "+value+", inverse = "+inverse)
}

```
lazy val 只计算一次，所以不能使用 var变量
    


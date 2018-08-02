# Case Class And Pattern Matching 

## 简单示例
### Case Class
形式
```scala

    abstract class Expr
    case class Var(name: String) extends Expr
    case class Number(num: Double) extends Expr
    case class UnOp(operator: String, arg: Expr) extends Expr
    case class BinOp(operator: String,
    left: Expr, right: Expr) extends Expr
    
```
1. `case class` 会自动添加以类名为名称的工厂方法，因此可以用以下方式创建实例
```scala
    val v = Var("x")
``` 
2. 所有的参数列表上的参数会隐式地提供一个 `val` 前缀，他们会作为域来保持：
```scala
    v.name
```
3. 编译器会以“自然”的方式实现 `toString`, `hashCode`,`equals`。
```scala
    print(v)

```
4. 编译器会自动添加一个 `copy` 方法生成修改后的复本。
```scala
    op.copy(operator = "-")

```

### 模式匹配
```scala
UnOp("-", UnOp("-", null)) => null // Double negation
BinOp("+", null, Number(0)) => null // Adding zero
BinOp("*", null, Number(1)) => null // Multiplying by one
```
常量匹配 如 "a"，1 相当于 "=="

变量匹配，如 e 匹配任意一个值

`_` 匹配任意值

构造器匹配，如 `UnOp("-",e)`。这个模式会匹配所有类型 `UnOp`中第一个参数为"-"，且
第二个参数为 e （e 可以是任意值）。注意：构造器中的参数本身也是模式。这样允许你使用 
简洁的表达式写深度匹配，如下所示：
```scala
    UnOp("-",UnOp("-",e))
```

### 匹配与 switch

不同之处：
1. 在scala里匹配是表达式(表达式用户返回一个值)
2. Scala 中子句表达式用户不会 "fall through"（穿透）到下一个 case
3. 如果没有模式匹配到， Scala 会抛出 MatchError 异常

## 15.2 模式匹配的种类
1. 通配符模式
 ```scala
    expr match {
        case BinOp(op, left, right) =>
        println(expr + " is a binary operation")
        case _ => // handle the default case
    }
    expr match {
    case BinOp(_, _, _) => println(expr + " is a binary operation")
    case _ => println("It's something else")
    }

```
2. 常量模式
```scala
    def describe(x: Any) = x match {
        case 5 => "five"
        case true => "truth"
        case "hello" => "hi!"
        case Nil => "the empty list"
        case _ => "something else"
    }
```
3. 变量匹配
```scala
expr match {
    case 0 => "zero"
    case somethingElse => "not zero: " + somethingElse
}

```
编译器是如何知道一个符号是变量还是常量？Scala 使用一个简单的词法规则来消除歧义：
如果一个名称是以小写字母开头的会被识别为变量模式，其它的都会认为是常量模式
```scala
import math.{E, Pi}
import math.{E, Pi}
E match {
  case Pi => "strange math? Pi = " + Pi
  case _ => "OK"
}

```
下面的 pi 会被认为是 变量，这个时候不需要 `_` ，加上它会报错
```scala

E match {
  case pi => "strange math? Pi = " + pi
  case _ => "OK"
}
```
4. 构造器模式
形式如下：下面模式的意思是先检查对象是不是 case class 类的一员，其次检查构造器参数。
```scala
    expr match {
    case BinOp("+", e, Number(0)) => println("a deep match")
    case _ =>
    }

```

5. 序列（列表）模式
形式如下：
```scala
    expr match {
        case List(0, _, _) => println("found it")
        case _ =>
    }

```
如果不想指定这个序列有多长，可以用 `_*` 作为最后一个元素，如下所示
```scala
    expr match {
        case List(0, _*) => println("found it")
        case _ =>
    }

```

6. 元组匹配
形式如下：
```scala
    //匹配三元组
    def tupleDemo(expr: Any) =
      expr match {
        case (a, b, c) => println("matched " + a + b + c)
        case _ =>
      }

```
7. 类型模式
```scala
    def generalSize(x : Any) = x match  {
      case s : String => s.length
      case m : Map[_,_] => m.size
      case _ => -1
      
    }

```
如果要测试一个对象是否属于一个类可以用 `expr.isInstanceOf[String]`。
如果转换成另一个类，比如转换成字符串，可以使用`expr.asInstanceOf[String]`。
但不推荐这么做，更好的用法是使用类型模式匹配。

8. 类型擦除
Scala 与 java 一样也是泛型也是使用类型擦除 （erasure）。因此下面的表达式会给出警告
```scala
    def isIntIntMap(x: Any) = x match {
        case m: Map[Int, Int] => true
        case _ => false
    }
/**
* scala> isIntIntMap(Map(1 -> 1))
  res19: Boolean = true
  scala> isIntIntMap(Map("abc" -> "abc"))
  res20: Boolean = true
*/

```
唯一的例外是数组，因为数组元素的类型在运行时会保存

```scala
def isStringArray(x: Any) = x match {
  case a: Array[String] => "yes"
  case _ => "no"
}

val as = Array("abc")
isStringArray(as)
// res: String = yes
val ai = Array(1, 2, 3)
isStringArray(ai)
// res: String = no

```

9. 变量绑定
可以在一个模式前面加一个变量名称 以 `@` 分割，这就是模式就绑定到一个变量上了，

形式：
```scala
    expr match {
      case UnOp("abs", e @ UnOp("abs",_)) => e
      case _ => 
    }
```

## 15.3 模式守卫
模式守卫是指在模式后面以 `if` 开头的部分，这个守卫是一个 `boolean` 表达式,它会引用
模式中的变量。如果存在模式守卫只有匹配成功且守卫计算结果为`true` 才能通过。
```scala
    def simplifyAdd(e: Expr) = e match {
      case BinOp("+", x, y) if x == y => BinOp("*", x, Number(2))
      case _ => e
    }

```
## 15.4 模式重叠






# Chapter 20 Abstract Members
如果一个类或者特征（`trait`）里的成员没有完整定义，该成员就是抽象的。 抽象成员通常由子类实现。
这个主意可以在许多面向对象的语言里发现。例如，`java` 可以让你定义抽象方法。scala 也允许你定义
这样的方法，就像你在 10.2 看到的一样。但是 scala 超越了这些，scala 完整的实现了抽象成员的概念(`generality`)，
而不仅仅是抽象方法，你可以定义一个抽象属性、抽象类型。

在这一章里我们会描述所有这些抽象成员：不可变变量(`vals`)、可变变量(`vars`)、方法(`methods`)和类型(`types`)。
顺便我们会讨论预初始化(`pre-initialized`)属性，惰性变量(`lazy vals`)，路径依赖类型(`path-dependent types`)
和枚举(`enumeration`)。

## 20.1 快速瀏覽抽象成员
下面的 `trait` 定义每一种抽象成员定义了一个：一个抽象类型(`T`)、方法(`transform`)、val(`initial`)和 var(`current`)
```scala
    trait Abstract {
      type T
      def transform(x : T) : T
      val initial: T
      var current: T
    }

```
一个具体的实现需要完整定义父类的每个抽象成员，下面是一个具体的实现：
```scala
    class Contrete extends Abstract {
      type T = String
      def transform(s:String) = x + x
      val initial = "hi"
      var current = initial
    }

```
这个实现给`T`定义了一个具体的实现，将`T`定义为`String`.


 


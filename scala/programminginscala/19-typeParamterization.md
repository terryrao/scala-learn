# Type Parameterization
java 里的泛型是协变 （`covariant` ）的，比方说 `List<Object>` 可以添加 `String` 元素,
scala 里的参数类型(`type parameter`)不是协变的，因此 `List<Anyref>` 不可以加 `String` 元素,scala 
协变形式：
```scala
    trait Queue1[+T] {} // 协变形式
    
    trait Queue2[-T] {} //逆协变形式
```

## a type parameter with lower bound
参数类型下限形式：`U >: T`，表示：`T` 是类型 `U` 的类型下界

```scala
    def orderedMergeSort[T <: Ordered[T]](xs: List[T]): List[T] = {
        def merge(xs: List[T], ys: List[T]): List[T] =
            (xs, ys) match {
                case (Nil, _) => ys
                case (_, Nil) => xs
                case (x :: xs1, y :: ys1) =>
                if (x < y) x :: merge(xs1, ys)
                else y :: merge(xs, ys1)
        }
        val n = xs.length / 2
        if (n == 0) xs
         else {
            val (ys, zs) = xs splitAt n
            merge(orderedMergeSort(ys), orderedMergeSort(zs))
        } 
    }

```
为了获取传入到你的新的混入了Ordered 类的排序函数的 list 的类型，你需要用到上界。上界非常类型于下限，上了下界是使用
 `>:` 符号，上界使用 `<:` 符号
 
 使用 `T <: Ordered[T]` 语法，就指定了参数类型 `T` 有一个上界 `Ordered[T]` 。这意味着传入到 `orderedMegeSort` 的
 list 的元素类型必须是 `Ordered` 的子类型。因此你可以传入一个 `List[Person]` 到 `orderedMergeSort` 中，因为 `Person`
 混入了 `Ordered` 。
 
 

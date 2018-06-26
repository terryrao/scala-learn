# Advanced Object-oriented Programming In Scala

# case class 

添加 ``case`` 关键编译器会认为与模式匹配有关系，编译器会添加如下功能：

1. constructor 参数列表 会自动处理成 val 类型
2. 编译器会自动生成 equals hashCode 和 toString 方法
3. 自动生成伴生对象 apply 工厂方法会生成与主构造器一样的参数列表
4. 自动生成的一个 copy 菜单

note： case class 可以重载构造器 但是不会重写 apply 方法 如果要用这个构造器就必须使用 new 

# The Equal Method 
##  == and !=
 
== delegates to equals ，所以 == 实际上调用的是 equals 方法
## eq and ne

如果使用内存地址比较 则需要使用 eq 方法，该方法只能用 AnyRef ,ne 不等于

## Array 比较
Array 不能使用 == 来判断两个数组是否相等
只能用 sameElements 方法判断
```scala
Array(1, 2).sameElements(Array(1, 2))
```





# The Scala Type System
## type keyword
1. 给类或者变量取一个别名，如 ``type String = java.lang.String``,``type Pair[+A, +B] = Tuple2[A, B]``

## 创建 Two-Item Tuple 的四种方式 
    1. ("hello",3.14)
    2. Pair("hello",3.14)
    3. Tuple2(“Hello”, 3.14)
    4. “Hello” → 3.14

## Class and objects
object 修饰的对象没有构造参数列表，也不能显示指定
Objects 会在运行时通过懒加载的方式自动创建出来 Objects are instantiated automatically and lazily by the runtime system






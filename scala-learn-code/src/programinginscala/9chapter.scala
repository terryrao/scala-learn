import java.io.{File, PrintWriter}
import java.util.Date

object FileMathcer {
  private def fileHere =new File("").listFiles()

  def filesEnding(query : String) =
    for (file <- fileHere; if file.getName.endsWith((query)))
      yield file

  def filesContaining(query : String) =
    for (file <- fileHere; if file.getName.contains(query))
      yield file
}


 // curry

def curriedSum(x : Int) (y : Int) = x + y

val openPlus = curriedSum(1) _

def first(x : Int) = (y : Int) => x + y

val second  = first(1)


def twice(op : Double => Double, x : Double) = op(op(x))

twice(_ + 1,5)

// Any time you find a control pattern repeated in multiple parts of your code, you should think about
//implementing it as a new control structure

def withPrintWriter(file : File, op : PrintWriter => Unit)= {
  val writer = new PrintWriter(file)
  op(writer)
}

withPrintWriter(new File("data.txt"),writer => writer.println(new Date().toString));


// BY-NAME PARAMETERS

var assertionsEnabled = true
def myAssert(predicate: () => Boolean) =
  if (assertionsEnabled && !predicate())
    throw new AssertionError

myAssert(() => 5 > 3)

myAssert(5 > 3) // Won't work, because missing () =>

def byNameAssert(predicate: => Boolean) =
  if (assertionsEnabled && !predicate)
    throw new AssertionError

byNameAssert(5 > 3)


def boolAssert(predicate: Boolean) =
  if (assertionsEnabled && !predicate)
    throw new AssertionError

boolAssert(5 > 3)
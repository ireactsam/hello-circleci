import org.scalatest.FunSuite

class HelloTest extends FunSuite {

  test("Hello") {
    assertResult("Hello World!") {
      Seq("Hello", "World!").mkString(" ")
    }
  }
}

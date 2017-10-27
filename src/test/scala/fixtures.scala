import scala.util.Random
import org.scalatest.{ fixture, _ }

object FixtureUtils {
  def fixtureFor[A](construct: => A) = (test: A => Any) => test(construct)
}

class FixTest4 extends FunSuite {
  import FixtureUtils._

  val withX = fixtureFor {
    s"MyTest ${Random.nextInt()}"
  }

  test("test 1") {
    withX { x =>
      println(x)
      assert(x.startsWith("MyTest"))
    }
  }
  test("test 2") {
    withX { x =>
      println(x)
      assert(x.startsWith("MyTest"))
    }
  }
}

class FixTest3 extends FunSuite {
  def withX(test: String => Any): Unit = {
    val x = s"MyTest ${Random.nextInt()}"
    test(x)
  }

  test("test 1") {
    withX { x =>
      println(x)
      assert(x.startsWith("MyTest"))
    }
  }
  test("test 2") {
    withX { x =>
      println(x)
      assert(x.startsWith("MyTest"))
    }
  }
}

class FixTest2 extends FunSuite {
  trait MyFix {
    val x = s"MyTest ${Random.nextInt()}"
  }

  test("test 1") {
    new MyFix {
      println(x)
      assert(x.startsWith("MyTest"))
    }
  }
  test("test 2") {
    new MyFix {
      println(x)
      assert(x.startsWith("MyTest"))
    }
  }
}

class FixTest1 extends fixture.FunSuite {
  override type FixtureParam = String
  override def withFixture(test: OneArgTest) = {
    val constructedX = s"MyTest ${Random.nextInt()}"
    test(constructedX)
  }

  test("test 1") { x =>
    println(x)
    assert(x.startsWith("MyTest"))
  }
  test("test 2") { x =>
    println(x)
    assert(x.startsWith("MyTest"))
  }
}

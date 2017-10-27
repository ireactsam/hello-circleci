import org.scalatest.{ FunSuite, Ignore }

@Ignore
class FailingTest extends FunSuite {

  test("Failing test") {
    fail("This test is just doomed to fail")
  }
}

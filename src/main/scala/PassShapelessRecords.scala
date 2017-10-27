import scala.annotation.implicitNotFound
import shapeless._
import shapeless.ops.record.Selector
import shapeless.record._
import shapeless.syntax.singleton._

object PassShapelessRecords extends App {

  /* Define Inputs record with fields foo: Int, bar: String, names: List[String] */

  type HasFoo[L <: HList] = Selector.Aux[L, Witness.`"foo"`.T, Int]
  type HasBar[L <: HList] = Selector.Aux[L, Witness.`"bar"`.T, String]
  type HasNames[L <: HList] = Selector.Aux[L, Witness.`"names"`.T, List[String]]

  @implicitNotFound("${L} should have foo, bar and names")
  case class FInputs[L <: HList](implicit s1: HasFoo[L], s2: HasBar[L], s3: HasNames[L])
  /* to avoid the deprecation warning, define it as:
  case class Inputs[L <: HList : HasFoo : HasBar : HasNames]() {
    val s1 = implicitly[HasFoo[L]]
    val s2 = implicitly[HasBar[L]]
    val s3 = implicitly[HasNames[L]]
  }
  */
  object FInputs {
    implicit def make[L <: HList: HasFoo: HasBar: HasNames]: FInputs[L] = FInputs[L]
  }

  /* Define Outputs record (it is strictly defined, order of the elements, value types, key names) */

  type FOutputs = Record.`"out" -> Int, "lengths" -> List[Int]`.T

  /* Function f and calling it */

  def f[L <: HList: FInputs](l: L): FOutputs = {
    val selectors = implicitly[FInputs[L]]
    import selectors._; productArity _ // dirty trick to avoid IntelliJ removing the import
    val x: Int = l("foo")
    val y: String = l("bar")
    val z: List[String] = l("names")
    val lengths = z.map(_.length)

    val result = x + y.length + lengths.sum

    ("out" ->> result) :: ("lengths" ->> lengths) :: HNil
  }

  val outputs1 = ("bar" ->> "Sam") :: ("foo" ->> 4) :: HNil
  val outputs2 = ("foobar" ->> 3.6d) :: ("names" ->> List("Natascha", "Alexander", "Koen")) :: HNil

  // notice how you can combine inputs and pass them, compile time check happens if all key:values are present
  val fOut = f(outputs1 ++ outputs2)
  println(s"Result fff = $fOut")

  /* The output can be used as input again, here loosely defined as a record having at least out: Int and lengths: List[Int] */

  type HasOut[L <: HList] = Selector.Aux[L, Witness.`"out"`.T, Int]
  type HasLengths[L <: HList] = Selector.Aux[L, Witness.`"lengths"`.T, List[Int]]

  @implicitNotFound("${L} should have out: Int and lengths: List[String]")
  case class GInputs[L <: HList](implicit s1: HasOut[L], s2: HasLengths[L])
  object GInputs {
    implicit def make[L <: HList: HasOut: HasLengths]: GInputs[L] = GInputs[L]
  }

  def g[L <: HList: GInputs](l: L) = {
    val selectors = implicitly[GInputs[L]]
    import selectors._; productArity _ // dirty trick to avoid IntelliJ removing the import
    val i: Int = l("out")
    val li: List[Int] = l("lengths")
    li.map(_ * i).sum
  }

  println(s"FINAL = ${g(fOut)}")
}

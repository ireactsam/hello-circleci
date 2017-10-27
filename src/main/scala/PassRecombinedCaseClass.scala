import shapeless._
import shapeless.ops.hlist.{ Align, Intersection, Prepend }

object PassRecombinedCaseClass extends App {

  // How to recombine multi case classes to a new case class,
  // reshuffling order of properties,
  // combine properties of multi case classes,
  // and drop unneeded properties
  // /!\ overlaps ==> first matching is taken

  case class Source1(foo: Int, bar: String, ignore: Boolean)
  case class Source2(foobar: Double, names: List[String], bar: String)

  case class Input(bar: String, foo: Int, names: List[String])

  def f1: Source1 = Source1(3, "fre", ignore = true)
  def f2: Source2 = Source2(4d, List("Nat"), "sam")

  def g(i: Input): Unit = println(i)

  // glue

  val s1G = LabelledGeneric[Source1]
  val s2G = LabelledGeneric[Source2]

  val iG = LabelledGeneric[Input]

  val prepend = Prepend[s2G.Repr, s1G.Repr]
  val intersect = Intersection[prepend.Out, iG.Repr]
  val align = Align[intersect.Out, iG.Repr]

  // call

  g(iG.from(align(intersect(prepend(s2G.to(f2), s1G.to(f1))))))
}

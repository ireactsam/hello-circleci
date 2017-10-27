case class IceCream(name: String, numCherries: Int)
case class I2Cream(ice: IceCream, count: Int)

trait CsvEncoder[A] {
  def encode(value: A): List[String]
}

object CsvEncoder {
  // "Summoner" method
  def apply[A](implicit enc: CsvEncoder[A]): CsvEncoder[A] =
    enc

  // "Constructor" method
  def instance[A](func: A => List[String]): CsvEncoder[A] =
    new CsvEncoder[A] {
      def encode(value: A): List[String] =
        func(value)
    }
  // SHORTHANDED to (scala 2.12 / Java 8):
  //(value: A) => func(value)

  // Globally visible type class instances
}

import shapeless._
object Playground {

  // specific
  /*
  implicit val iceCreamEncoder = CsvEncoder.instance[IceCream](iceCream => List(iceCream.name, iceCream.numCherries.toString))
  */

  // more generic
  implicit val stringEncoder: CsvEncoder[String] = CsvEncoder.instance[String](s => List(s))

  implicit val intEncoder: CsvEncoder[Int] = CsvEncoder.instance[Int](i => List(i.toString))

  implicit val hnilEncoder: CsvEncoder[HNil] = CsvEncoder.instance[HNil](hnil => Nil)

  // next step: Lazy head HList encoder to support nested stuctures
  implicit def hlistEncoder[H, T <: HList](implicit hEncoder: Lazy[CsvEncoder[H]], tEncoder: CsvEncoder[T]): CsvEncoder[::[H, T]] = CsvEncoder.instance[H :: T] {
    case h :: t => hEncoder.value.encode(h) ++ tEncoder.encode(t)
  }
  /*
  implicit val iceCreamEncoder = CsvEncoder.instance[IceCream] { iceCream =>
    val gen = Generic[IceCream]
    val enc = CsvEncoder[gen.Repr]
    enc.encode(gen.to(iceCream))
  }
  */

  // most generic
  // next step: Lazy Repr encoder of Generic rule to support nested stuctures
  implicit def genericEncoder[A, R](implicit gen: Generic.Aux[A, R], rEnc: Lazy[CsvEncoder[R]]): CsvEncoder[A] =
    CsvEncoder.instance[A](a => rEnc.value.encode(gen.to(a)))

  // => now you can write immediatly:
  val r: Seq[String] = CsvEncoder[I2Cream].encode(I2Cream(IceCream("x", 1), 2))
}


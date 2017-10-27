object Source {
  case class NameAndMore(id: Long, name: String, lastName: String)
  case class AgeAndMore(id: Long, sex: String, age: Int)
  case class Output1(ns: List[NameAndMore])
  case class Output2(as: List[AgeAndMore])
}

object Trans {
  case class Name(id: Long, name: String)
  case class Age(id: Long, age: Int)
  case class Input(l1: List[Name], l2: List[Age])

  case class Combined(id: Long, age: Int, name: String, isFlag: Boolean)
  case class Output(l: List[Combined])

  def transform(i: Input): Output = {
    // some magic that combines i.l1 and i.l2 into some output
    val joined = for {
      name <- i.l1
      age <- i.l2
      if name.id == age.id
    } yield (name, age)
    Output(joined.map { case (name, age) => Combined(name.id, age.age, name.name, isFlag = true) })
  }
}

object Transformations extends App {

  def s1 = Source.Output1(List(Source.NameAndMore(1L, "Sam", "De Backer")))

}


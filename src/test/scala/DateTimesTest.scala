
import java.sql.Timestamp
import java.time.{ ZoneOffset, ZonedDateTime }
import scala.concurrent.duration._
import org.scalatest.FunSuite

class DateTimesTest extends FunSuite {
  import DateTimes._

  test("parseAsUTC") {
    assert(parseAsUTC("2017-10-13T13:14:15.999Z") === ZonedDateTime.of(2017, 10, 13, 13, 14, 15, 999.millis.toNanos.toInt, ZoneOffset.UTC))
    assert(parseAsUTC("2017-10-13T13:14:15.999") === ZonedDateTime.of(2017, 10, 13, 13, 14, 15, 999.millis.toNanos.toInt, ZoneOffset.UTC))
    assert(parseAsUTC("2017-10-13T13:14:15") === ZonedDateTime.of(2017, 10, 13, 13, 14, 15, 0, ZoneOffset.UTC))
    assert(parseAsUTC("2017-10-13 13:14:15.999") === ZonedDateTime.of(2017, 10, 13, 13, 14, 15, 999.millis.toNanos.toInt, ZoneOffset.UTC))
    assert(parseAsUTC("2017-10-13 13:14:15") === ZonedDateTime.of(2017, 10, 13, 13, 14, 15, 0, ZoneOffset.UTC))
  }

  test("to/from timestamp") {
    val ts = Timestamp.from(parseAsUTC("2017-10-13T13:14:15.999Z").toInstant)
    val zdt = ZonedDateTime.ofInstant(ts.toInstant, ZoneOffset.UTC)

    assert(zdt === ZonedDateTime.of(2017, 10, 13, 13, 14, 15, 999.millis.toNanos.toInt, ZoneOffset.UTC))
  }
}

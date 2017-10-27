import java.time.format.DateTimeFormatter
import java.time.{ ZoneOffset, ZonedDateTime }

object DateTimes {
  private[this] val fmt = DateTimeFormatter.ofPattern(
    "[yyyy-MM-dd'T'HH:mm:ss[.SSS['Z']]]" +
      "[yyyy-MM-dd' 'HH:mm:ss[.SSS]]").withZone(ZoneOffset.UTC)

  def parseAsUTC(s: String): ZonedDateTime =
    ZonedDateTime.parse(s, fmt)
}

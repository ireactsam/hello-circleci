object Utils {
  private val VersionRegex = "v?([0-9]+.[0-9]+.[0-9]+)-?(.*)?".r
  val tagToVersion = { tag: String =>
    val version = tag match {
      case VersionRegex(v,"") => Some(v)
      case VersionRegex(v,"SNAPSHOT") => Some(s"$v-SNAPSHOT")
      case VersionRegex(v,s) => Some(s"$v-$s-SNAPSHOT")
      case _ => None
    }
    println(s"tag: $tag --> version: $version")
    version
  }
}

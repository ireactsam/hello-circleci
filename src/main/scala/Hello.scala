import io.ireact.BuildInfo

object Hello extends App {

  println(s"Hello from ${BuildInfo.name} ${BuildInfo.version} build at ${BuildInfo.builtAtString} UTC.")
  println("Go home")
}

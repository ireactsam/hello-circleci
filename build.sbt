import Dependencies._

//val `dpg-datalake-artifacts` = "s3://s3-eu-west-1.amazonaws.com/dpg-datalake-artifacts"
//val `dpg-ngage-artifacts` =  "s3://s3-eu-west-1.amazonaws.com/dpg-ngage-artifacts"

val commonSettings = List(
  organization := "io.ireact",
  //version      := "0.0.1-SNAPSHOT", // version defined by sbt-dynver (or sbt-git)  plugin, do not set it!

  // sbt-git
  git.useGitDescribe := true,
  git.baseVersion := "0.0.0",
  git.gitTagToVersionNumber := Utils.tagToVersion,

  //resolvers ++= Seq(
  //  "dpg-datalake-artifacts Releases"  at s"${`dpg-datalake-artifacts`}/release",
  //  "dpg-datalake-artifacts Snapshots" at s"${`dpg-datalake-artifacts`}/snapshot"
  //),

  scalaVersion := "2.11.11",
  //scalacOptions ++= Seq("-unchecked", "-deprecation"),
  scalacOptions ++= Seq("-Xlint:_,-nullary-unit,-delayedinit-select", "-feature", "-unchecked", "-deprecation", "-encoding", "utf8", "-Ywarn-unused-import", "-Ywarn-dead-code", "-Ywarn-adapted-args", "-Ywarn-infer-any", "-Ywarn-inaccessible", "-Ywarn-numeric-widen"),
  // For the following there are some false positives, or things we cannot do much about... add them to check if you have no new cases
  scalacOptions ++= Seq("-Ywarn-unused", "-Ywarn-value-discard", "-Ywarn-nullary-unit", "-Ywarn-nullary-override"),
  scapegoatVersion := "1.3.3",
  scapegoatDisabledInspections := Seq("FinalModifierOnCaseClass")

  //publishTo := {
  //  if (isSnapshot.value)
  //    Some("snapshots" at s"${`dpg-ngage-artifacts`}/snapshots")
  //  else
  //    Some("releases"  at s"${`dpg-ngage-artifacts`}/releases")
  //}
)

lazy val isCI = sys.env.getOrElse("CI", "false").toBoolean
def disabledPlugins =
  if (isCI) Seq(
    //ScapegoatSbtPlugin, // seems to be buggy to try to exclude it
    SbtScalariform,
    scoverage.ScoverageSbtPlugin
  ) else Nil

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin, GitVersioning /* sbt-git: , GitVersioning, GitBranchPrompt*/)
  .disablePlugins(disabledPlugins: _*)
  .settings(
    commonSettings,
    name := "hello-circleci",
    libraryDependencies ++= Seq(
      //datalakeCore,
      //datalakeTesting % Test,
      scalaTest % Test,
      shapeless,
      sparkCore % Provided,
      sparkHive % Provided,
      sparkSql % Provided
    ),
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoOptions += BuildInfoOption.BuildTime,
    buildInfoPackage := organization.value//,
    // uncomment to prevent test during assembly
    // test in assembly := {},
    /*
    artifact in (Compile, assembly) := {
      val art = (artifact in (Compile, assembly)).value
      art.withClassifier(Some("assembly"))
    },
    addArtifact(artifact in (Compile, assembly), assembly),
    assemblyMergeStrategy in assembly := {
      case PathList("org","aopalliance", xs @ _*) => MergeStrategy.last
      case PathList("javax", "inject", xs @ _*) => MergeStrategy.last
      case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
      case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
      case PathList("org", "apache", xs @ _*) => MergeStrategy.last
      case PathList("com", "google", xs @ _*) => MergeStrategy.last
      case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
      case PathList("com", "codahale", xs @ _*) => MergeStrategy.last
      case PathList("com", "yammer", xs @ _*) => MergeStrategy.last
      case "about.html" => MergeStrategy.rename
      case "META-INF/ECLIPSEF.RSA" => MergeStrategy.last
      case "META-INF/mailcap" => MergeStrategy.last
      case "META-INF/mimetypes.default" => MergeStrategy.last
      case "META-INF/io.netty.versions.properties" => MergeStrategy.last
      case "plugin.properties" => MergeStrategy.last
      case "log4j.properties" => MergeStrategy.last
      case x =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
    },
    assemblyExcludedJars in assembly := {
      val cp = (fullClasspath in assembly).value
      // exclude the scapegoat plugin that is sneaking into the assembled jar
      cp filter {_.data.getName.matches("scalac-scapegoat-plugin.*\\.jar")}
    }
    */
  )
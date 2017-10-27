import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4"
  
  lazy val shapeless = "com.chuusai" %% "shapeless" % "2.3.2"

  val sparkVersion = "2.1.0"
  lazy val sparkCore = "org.apache.spark" %% "spark-core" % sparkVersion
  lazy val sparkHive = "org.apache.spark" %% "spark-hive" % sparkVersion
  lazy val sparkSql = "org.apache.spark" %% "spark-sql" % sparkVersion
}

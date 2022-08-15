import Dependencies._

ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.iilab"
ThisBuild / organizationName := "it-innovation-lab"

lazy val root = (project in file("."))
  .settings(
    name := "spark-ml",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq(
      "org.apache.spark" % "spark-core_2.13" % "3.2.1",
      "org.apache.spark" % "spark-sql_2.13" % "3.2.1",
      "org.apache.spark" % "spark-mllib_2.13" % "3.2.1"
    )
  )


// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.

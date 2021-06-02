ThisBuild / scalaVersion := "2.13.6"

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "19.0",
  "org.scalactic" %% "scalactic" % "3.2.9",
  "org.scalatest" %% "scalatest" % "3.2.9" % "test"
)
scalacOptions ++= Seq("-deprecation", "-feature")

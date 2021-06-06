ThisBuild / scalaVersion := "3.0.0"

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "30.1.1-jre",
  "org.scalactic" %% "scalactic" % "3.2.9",
  "org.scalatest" %% "scalatest" % "3.2.9" % "test"
)

javacOptions += "-Xlint:unchecked"

scalacOptions ++= {
  Seq(
    "-encoding",
    "UTF-8",
    "-feature",
    // disabled during the migration
    // "-Xfatal-warnings"
  ) ++
    (CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((3, _)) => Seq(
        "-unchecked",
        "-source:3.0-migration"
      )
      case _ => Seq(
        "-deprecation",
        "-Xfatal-warnings",
        "-Wunused:imports,privates,locals",
      )
    })
}

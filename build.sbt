name := "play-pdm"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc41",
  "commons-codec" % "commons-codec" % "1.9",
  "commons-io" % "commons-io" % "2.4"
)     

play.Project.playJavaSettings

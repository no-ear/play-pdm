name := "play-pdm"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.25",
  "commons-codec" % "commons-codec" % "1.9"
)     

play.Project.playJavaSettings

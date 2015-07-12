name := "scala-website"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.11.7",
  "mysql" % "mysql-connector-java" % "5.1.36",
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "org.slf4j" % "slf4j-log4j12" % "1.7.12"
  )

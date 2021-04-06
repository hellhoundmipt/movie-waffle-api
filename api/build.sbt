name := "akka-quickstart-scala"

version := "1.0"

scalaVersion := "2.13.4"

lazy val akkaHttpVersion = "10.2.2"
lazy val akkaVersion = "2.6.11"

// Akka
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
)

// Akka http
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
)

// Slick 
libraryDependencies ++=Seq(
  "com.typesafe.slick" %% "slick" % "3.3.2",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
  "org.postgresql" % "postgresql" % "42.2.5"
)

// Alpakka
libraryDependencies ++= Seq(
  "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "2.0.2",
)

// Logs
libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
)

// Misc
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.1.0" % Test
)
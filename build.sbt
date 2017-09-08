name := "scalaJVM"

version := "0.1"

scalaVersion := "2.12.3"

libraryDependencies += "commons-cli" % "commons-cli" % "1.4"



mainClass in (Compile, packageBin) := Some("com.sword865.scalaJVM.scalaJVM")

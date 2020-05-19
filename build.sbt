name := "software-design"

version := "0.1"

scalaVersion := "2.12.8"


libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.2" % "test"

testFrameworks += new TestFramework("utest.runner.Framework")

libraryDependencies += "junit" % "junit" % "4.12" % Test
libraryDependencies += "commons-cli" % "commons-cli" % "1.4"

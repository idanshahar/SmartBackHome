name := """smartBackHome"""

version := "1.2-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

libraryDependencies ++= Seq(
  "com.google.code.gson" % "gson" % "2.8.1",
  "com.microsoft.azure.sdk.iot" % "iot-service-client" % "1.9.24"
)

dockerExposedPorts := Seq(9000, 9443)

packageName in Docker := packageName.value

version in Docker := version.value

dockerBaseImage := "openjdk"

dockerExposedPorts := Seq(9000, 9443)

dockerExposedVolumes := Seq("/opt/docker/logs")

defaultLinuxInstallLocation in Docker := "/opt/docker"

dockerRepository := Some("backhomeregistry.azurecr.io")

dockerUpdateLatest := true
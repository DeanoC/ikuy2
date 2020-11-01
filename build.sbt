lazy val spinalCore =
  ProjectRef(uri("git://github.com/DeanoC/SpinalHDL.git#dev"), "core")
lazy val spinalLib =
  ProjectRef(uri("git://github.com/DeanoC/SpinalHDL.git#dev"), "lib")
lazy val spinalPlugin =
  ProjectRef(uri("git://github.com/DeanoC/SpinalHDL.git#dev"), "idslplugin")

val sharedSettings = Seq(
  scalaVersion := "2.11.12",
  organization := "com.deanoc",
  version := "1.0",
  scalacOptions ++= Seq("-deprecation"),
  licenses := Seq(
        "The MIT License (MIT)" -> url("http://opensource.org/licenses/MIT")
      )
)

lazy val common = (project in (file("src/ikuy/common")))
  .settings(sharedSettings)

lazy val gateware = (project in (file("src/ikuy/gateware")))
  .settings(sharedSettings)
  .settings(
    scalacOptions +=
        (artifactPath in (spinalPlugin, Compile, packageBin)).map { file =>
          s"-Xplugin:${file.getAbsolutePath}"
        }.value
  )
  .dependsOn(spinalCore)
  .dependsOn(spinalLib)
  .dependsOn(spinalPlugin)
  .dependsOn(common)

lazy val hardware = (project in (file("src/ikuy/hardware")))
  .settings(sharedSettings)
  .dependsOn(common)

lazy val ikuy = project
  .in(file("src/ikuy"))
  .settings(sharedSettings)
  .dependsOn(common)
  .dependsOn(gateware)
  .dependsOn(hardware)

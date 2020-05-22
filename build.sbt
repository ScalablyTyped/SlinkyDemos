import java.nio.file.Files
import java.nio.file.StandardCopyOption.REPLACE_EXISTING

import scala.sys.process.Process

Global / onLoad := {
  println("""*
      |* Welcome to ScalablyTyped demos!
      |*
      |* These demos demonstrate how to use third party react components with Slinky.
      |*
      |* For documentation see https://scalablytyped.org .
      |*
      |* Note that the first time you import/compile the projects it'll take a while for the dependencies to build
      |*""".stripMargin)
  (Global / onLoad).value
}

// both intellij and ci needs this to not OOM during initial import since we have so many projects
Global / concurrentRestrictions ++= {
  val gigabytes = (java.lang.Runtime.getRuntime.maxMemory) / (1000 * 1000 * 1000)
  val numParallel = Math.max(1, gigabytes.toInt)
  List(Tags.limit(ScalablyTypedTag, numParallel))
}

// Uncomment if you want to remove debug output
//Global / stQuiet := true

/**
  * Custom task to start demo with webpack-dev-server, use as `<project>/start`.
  * Just `start` also works, and starts all frontend demos
  *
  * After that, the incantation is this to watch and compile on change:
  * `~<project>/fastOptJS::webpack`
  */
lazy val start = TaskKey[Unit]("start")

/** Say just `dist` or `<project>/dist` to make a production bundle in
  * `docs` for github publishing
  */
lazy val dist = TaskKey[File]("dist")

lazy val baseSettings: Project => Project =
  _.enablePlugins(ScalaJSPlugin)
    .settings(
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.13.2",
      scalacOptions ++= ScalacOptions.flags,
      scalaJSUseMainModuleInitializer := true,
      scalaJSLinkerConfig ~= (/* disabled because it somehow triggers many warnings */
      _.withSourceMap(false)
        .withModuleKind(ModuleKind.CommonJSModule)),
      /* for slinky */
      libraryDependencies ++= Seq("me.shadaj" %%% "slinky-hot" % "0.6.5"),
      scalacOptions += "-Ymacro-annotations"
    )

lazy val `react-mobx` =
  project
    .enablePlugins(ScalablyTypedConverterPlugin)
    .configure(baseSettings, browserProject, reactNpmDeps, bundlerSettings)
    .settings(
      useYarn := true,
      webpackDevServerPort := 8001,
      stFlavour := Flavour.Slinky,
      stEnableScalaJsDefined := Selection.All,
      stIgnore ++= List("material-ui/svg-icons"),
      Compile / npmDependencies ++= Seq(
        "axios" -> "0.19.0",
        "material-ui" -> "0.20.1",
        "mobx" -> "5.15.4",
        "mobx-react" -> "6.2.2",
        "@types/material-ui" -> "0.21.7"
      )
    )

lazy val `react-slick` =
  project
    .enablePlugins(ScalablyTypedConverterPlugin)
    .configure(baseSettings, browserProject, reactNpmDeps, bundlerSettings)
    .settings(
      useYarn := true,
      webpackDevServerPort := 8002,
      stFlavour := Flavour.Slinky,
      Compile / npmDependencies ++= Seq(
        "react-slick" -> "0.23",
        "@types/react-slick" -> "0.23.4"
      )
    )

lazy val `react-big-calendar` =
  project
    .enablePlugins(ScalablyTypedConverterPlugin)
    .configure(baseSettings, browserProject, withCssLoading, reactNpmDeps, bundlerSettings)
    .settings(
      useYarn := true,
      webpackDevServerPort := 8003,
      stFlavour := Flavour.Slinky,
      Compile / npmDependencies ++= Seq(
        "moment" -> "2.24.0",
        "react-big-calendar" -> "0.24.4",
        "@types/react-big-calendar" -> "0.24.0"
      )
    )

lazy val `semantic-ui-react-kitchensink` = project
  .enablePlugins(ScalablyTypedConverterPlugin)
  .configure(baseSettings, browserProject, reactNpmDeps, bundlerSettings)
  .settings(
    useYarn := true,
    webpackDevServerPort := 8004,
    stFlavour := Flavour.Slinky,
    stEnableScalaJsDefined := Selection.All,
    Compile / npmDependencies ++= Seq(
      "semantic-ui-react" -> "0.88.2"
    )
  )

/** Note: This can't use scalajs-bundler (at least I don't know how),
  *  so we run yarn ourselves with an external package.json.
  **/
lazy val `storybook-react` = project
  .enablePlugins(ScalablyTypedConverterExternalNpmPlugin)
  .configure(baseSettings)
  .settings(
    /* ScalablyTypedConverterExternalNpmPlugin requires that we define how to install node dependencies and where they are */
    externalNpm := {
      Process("yarn", baseDirectory.value).!
      baseDirectory.value
    },
    stFlavour := Flavour.Slinky,
    /** This is not suitable for development, but effective for demo.
      * Run `yarn storybook` commands yourself, and run `~storybook-react/fastOptJS` from sbt
      */
    start := {
      (Compile / fastOptJS).value
      Process("yarn storybook", baseDirectory.value).!
    },
    dist := {
      val distFolder = (ThisBuild / baseDirectory).value / "docs" / moduleName.value
      (Compile / fullOptJS).value
      Process("yarn dist", baseDirectory.value).!
      distFolder
    }
  )

lazy val antd =
  project
    .enablePlugins(ScalablyTypedConverterPlugin)
    .configure(baseSettings, browserProject, withCssLoading, reactNpmDeps, bundlerSettings)
    .settings(
      useYarn := true,
      webpackDevServerPort := 8006,
      stFlavour := Flavour.Slinky,
      Compile / npmDependencies ++= Seq("antd" -> "3.26.0") // todo: bump to 4
    )

lazy val `react-router-dom` =
  project
    .enablePlugins(ScalablyTypedConverterPlugin)
    .configure(baseSettings, browserProject, reactNpmDeps, bundlerSettings)
    .settings(
      useYarn := true,
      webpackDevServerPort := 8007,
      stFlavour := Flavour.Slinky,
      Compile / npmDependencies ++= Seq(
        "react-router-dom" -> "5.1.2",
        "@types/react-router-dom" -> "5.1.2" // note 5.1.4 did weird things to the Link component
      )
    )

lazy val `material-ui` =
  project
    .enablePlugins(ScalablyTypedConverterPlugin)
    .configure(baseSettings, browserProject, reactNpmDeps, bundlerSettings)
    .settings(
      useYarn := true,
      webpackDevServerPort := 8008,
      stFlavour := Flavour.Slinky,
      stEnableScalaJsDefined := Selection.AllExcept("@material-ui/core"),
      stIgnore ++= List("@material-ui/icons"),
      Compile / npmDependencies ++= Seq(
        "@material-ui/core" -> "3.9.3" // note: version 4 is not supported yet
      )
    )

lazy val `react-leaflet` = project
  .enablePlugins(ScalablyTypedConverterPlugin)
  .configure(baseSettings, browserProject, reactNpmDeps, withCssLoading, bundlerSettings)
  .settings(
    useYarn := true,
    webpackDevServerPort := 8009,
    stFlavour := Flavour.Slinky,
    Compile / npmDependencies ++= Seq(
      "react-leaflet" -> "2.6.3",
      "@types/react-leaflet" -> "2.5.1",
      "leaflet" -> "1.6.0"
    )
  )

lazy val `office-ui-fabric-react` = project
  .enablePlugins(ScalablyTypedConverterPlugin)
  .configure(baseSettings, browserProject, reactNpmDeps, bundlerSettings)
  .settings(
    useYarn := true,
    webpackDevServerPort := 8010,
    stFlavour := Flavour.Slinky,
    Compile / npmDependencies ++= Seq(
      "office-ui-fabric-react" -> "7.107.1"
    )
  )

/** Note: This can't use scalajs-bundler (at least I don't know how),
  *  so we run yarn ourselves with an external package.json.
  **/
lazy val `react-native` = project
  .enablePlugins(ScalablyTypedConverterExternalNpmPlugin)
  .configure(baseSettings)
  .settings(
    scalaJSUseMainModuleInitializer := false,
    /* ScalablyTypedConverterExternalNpmPlugin requires that we define how to install node dependencies and where they are */
    externalNpm := {
      Process("yarn", baseDirectory.value).!
      baseDirectory.value
    },
    stFlavour := Flavour.SlinkyNative,
    stStdlib := List("es5"),
    run := {
      (Compile / fastOptJS).value
      Process("expo start", baseDirectory.value).!
    }
  )

lazy val reactNpmDeps: Project => Project =
  _.settings(
    Compile / npmDependencies ++= Seq(
      "react" -> "16.13.1",
      "react-dom" -> "16.13.1",
      "@types/react" -> "16.9.34",
      "@types/react-dom" -> "16.9.6"
    )
  )

lazy val bundlerSettings: Project => Project =
  _.settings(
    Compile / fastOptJS / webpackExtraArgs += "--mode=development",
    Compile / fullOptJS / webpackExtraArgs += "--mode=production",
    Compile / fastOptJS / webpackDevServerExtraArgs += "--mode=development",
    Compile / fullOptJS / webpackDevServerExtraArgs += "--mode=production"
  )

lazy val withCssLoading: Project => Project =
  _.settings(
    /* custom webpack file to include css */
    webpackConfigFile := Some((ThisBuild / baseDirectory).value / "custom.webpack.config.js"),
    Compile / npmDevDependencies ++= Seq(
      "webpack-merge" -> "4.2.2",
      "css-loader" -> "3.4.2",
      "style-loader" -> "1.1.3",
      "file-loader" -> "5.1.0",
      "url-loader" -> "3.0.0"
    )
  )

/**
  * Implement the `start` and `dist` tasks defined above.
  * Most of this is really just to copy the index.html file around.
  */
lazy val browserProject: Project => Project =
  _.settings(
    start := {
      (Compile / fastOptJS / startWebpackDevServer).value
    },
    dist := {
      val artifacts = (Compile / fullOptJS / webpack).value
      val artifactFolder = (Compile / fullOptJS / crossTarget).value
      val distFolder = (ThisBuild / baseDirectory).value / "docs" / moduleName.value

      distFolder.mkdirs()
      artifacts.foreach { artifact =>
        val target = artifact.data.relativeTo(artifactFolder) match {
          case None          => distFolder / artifact.data.name
          case Some(relFile) => distFolder / relFile.toString
        }

        Files.copy(artifact.data.toPath, target.toPath, REPLACE_EXISTING)
      }

      val indexFrom = baseDirectory.value / "src/main/js/index.html"
      val indexTo = distFolder / "index.html"

      val indexPatchedContent = {
        import collection.JavaConverters._
        Files
          .readAllLines(indexFrom.toPath, IO.utf8)
          .asScala
          .map(_.replaceAllLiterally("-fastopt-", "-opt-"))
          .mkString("\n")
      }

      Files.write(indexTo.toPath, indexPatchedContent.getBytes(IO.utf8))
      distFolder
    }
  )

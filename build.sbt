import java.nio.file.Files
import java.nio.file.StandardCopyOption.REPLACE_EXISTING

import org.scalajs.core.tools.linker.ModuleKind

import scala.sys.process.Process

Global / onLoad := {
  println("""*
      |* Welcome to ScalablyTyped demos!
      |*
      |* These demos demonstrate how to use third party react components with Slinky.
      |*
      |* For documentation see https://scalablytyped.org .
      |*
      |* Note that compiling all the demos at once can be computationally quite expensive, so you might have a better experience running `<project>/start` than starting all with `start` (though you can!)
      |*""".stripMargin)
  (Global / onLoad).value
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
      scalaVersion := "2.13.1",
      scalacOptions ++= ScalacOptions.flags,
      scalaJSUseMainModuleInitializer := true,
      scalaJSLinkerConfig ~= (
        /* disabled because it somehow triggers many warnings */
        _.withSourceMap(false)
          .withModuleKind(ModuleKind.CommonJSModule)),
      /* for slinky */
      libraryDependencies ++= Seq("me.shadaj" %%% "slinky-hot" % "0.6.3"),
      scalacOptions += "-Ymacro-annotations"
    )

lazy val bundlerSettings: Project => Project =
  _.enablePlugins(ScalablyTypedConverterPlugin)
    .settings(
      Compile / fastOptJS / webpackExtraArgs += "--mode=development",
      Compile / fullOptJS / webpackExtraArgs += "--mode=production",
      Compile / fastOptJS / webpackDevServerExtraArgs += "--mode=development",
      Compile / fullOptJS / webpackDevServerExtraArgs += "--mode=production",
      useYarn := true
    )

lazy val `react-mobx` =
  project
    .configure(baseSettings, bundlerSettings, browserProject, reactNpmDeps)
    .settings(
      webpackDevServerPort := 8001,
      Compile / stFlavour := Flavour.Slinky,
      Compile / stEnableScalaJsDefined := Selection.All,
      Compile / stIgnore ++= List("material-ui/svg-icons"),
      Compile / stMinimize := Selection.AllExcept("axios", "mobx-react", "mobx"),
      Compile / stMinimizeKeep ++= List(
        "materialUi.lightBaseThemeMod.default",
        "materialUi.stylesMod.MuiTheme",
        "materialUi.stylesMod.getMuiTheme"
      ),
      Compile / npmDependencies ++= Seq(
        "axios" -> "0.19.0",
        "material-ui" -> "0.20.1",
        "mobx" -> "5.15.0",
        "mobx-react" -> "6.1.4",
        "@types/material-ui" -> "0.21.7"
      )
    )

lazy val `react-slick` =
  project
    .configure(baseSettings, bundlerSettings, browserProject, reactNpmDeps)
    .settings(
      webpackDevServerPort := 8002,
      Compile / stFlavour := Flavour.Slinky,
      Compile / stIgnore += "csstype",
      Compile / stMinimize := Selection.All,
      Compile / npmDependencies ++= Seq(
        "react-slick" -> "0.23",
        "@types/react-slick" -> "0.23.4"
      )
    )

lazy val `react-big-calendar` =
  project
    .configure(baseSettings, bundlerSettings, browserProject, withCssLoading, reactNpmDeps)
    .settings(
      webpackDevServerPort := 8003,
      Compile / stFlavour := Flavour.Slinky,
      Compile / stIgnore += "csstype",
      Compile / stMinimize := Selection.All,
      Compile / stMinimizeKeep ++= List(
        "moment.mod.^",
        "reactBigCalendar.mod.momentLocalizer",
        "reactBigCalendar.mod.View",
      ),
      Compile / npmDependencies ++= Seq(
        "moment" -> "2.24.0",
        "react-big-calendar" -> "0.22",
        "@types/react-big-calendar" -> "0.22.3"
      )
    )

lazy val `semantic-ui-react-kitchensink` = project
  .enablePlugins(ScalablyTypedConverterPlugin)
  .configure(baseSettings, bundlerSettings, browserProject, reactNpmDeps)
  .settings(
    webpackDevServerPort := 8004,
    Compile / stFlavour := Flavour.Slinky,
    Compile / stEnableScalaJsDefined := Selection.All,
    Compile / stIgnore += "csstype",
    Compile / stMinimize := Selection.All,
    Compile / npmDependencies ++= Seq(
      "semantic-ui-react" -> "0.88.1"
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
    Compile / stFlavour := Flavour.Slinky,
    Compile / stIgnore += "csstype",
    Compile / stMinimize := Selection.AllExcept("@storybook/react"),
    Compile / stMinimizeKeep ++= List(
      "node.module"
    ),
    /** This is not suitable for development, but effective for demo.
      * Run `yarn storybook` commands yourself, and run `~storybook-react/fastOptJS` from sbt
      */
    run := {
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
    .configure(baseSettings, bundlerSettings, browserProject, withCssLoading, reactNpmDeps)
    .settings(
      webpackDevServerPort := 8006,
      Compile / stFlavour := Flavour.Slinky,
      Compile / stIgnore += "csstype",
      Compile / stMinimize := Selection.AllExcept("antd"),
      Compile / npmDependencies ++= Seq("antd" -> "3.26.0")
    )

lazy val `react-router-dom` =
  project
    .configure(baseSettings, bundlerSettings, browserProject, reactNpmDeps)
    .settings(
      webpackDevServerPort := 8007,
      Compile / stFlavour := Flavour.Slinky,
      Compile / stIgnore += "csstype",
      Compile / stMinimize := Selection.All,
      Compile / npmDependencies ++= Seq(
        "react-router-dom" -> "5.1.2",
        "@types/react-router-dom" -> "5.1.2"
      )
    )

lazy val `material-ui` =
  project
    .configure(baseSettings, bundlerSettings, browserProject, reactNpmDeps)
    .settings(
      webpackDevServerPort := 8008,
      Compile / stFlavour := Flavour.Slinky,
      Compile / stMinimize := Selection.AllExcept("@material-ui/core"),
      Compile / stEnableScalaJsDefined := Selection.AllExcept("@material-ui/core"),
      Compile / stIgnore ++= List("@material-ui/icons", "csstype"),
      Compile / npmDependencies ++= Seq(
        "@material-ui/core" -> "3.9.3"
      )
    )

lazy val `react-leaflet` = project
  .enablePlugins(ScalablyTypedConverterPlugin)
  .configure(baseSettings, bundlerSettings, browserProject, reactNpmDeps, withCssLoading)
  .settings(
    webpackDevServerPort := 8009,
    Compile / stFlavour := Flavour.Slinky,
    Compile / stIgnore += "csstype",
    Compile / stMinimize := Selection.All,
    Compile / stMinimizeKeep ++= List(
      "leaflet.leafletRequire"
    ),
    Compile / npmDependencies ++= Seq(
      "react-leaflet" -> "2.5",
      "@types/react-leaflet" -> "2.5",
      "leaflet" -> "1.5"
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
    Compile / stFlavour := Flavour.SlinkyNative,
    Compile / stIgnore := List("csstype"),
    Compile / stMinimize := Selection.AllExcept("@ant-design/react-native", "expo-font", "expo"),
    /** This is not suitable for development, but effective for demo.
      * Run `expo start` yourself, and run `~react-native/fastOptJS` from sbt
      */
    run := {
      (Compile / fastOptJS).value
      Process("expo start", baseDirectory.value).!
    }
  )

lazy val reactNpmDeps: Project => Project =
  _.settings(
    Compile / npmDependencies ++= Seq(
      "react" -> "16.9",
      "react-dom" -> "16.9",
      "@types/react" -> "16.9.5"
    )
  )

lazy val withCssLoading: Project => Project =
  _.settings(
    /* custom webpack file to include css */
    webpackConfigFile := Some((ThisBuild / baseDirectory).value / "custom.webpack.config.js"),
    Compile / npmDevDependencies ++= Seq(
      "webpack-merge" -> "4.1",
      "css-loader" -> "2.1.0",
      "style-loader" -> "0.23.1",
      "file-loader" -> "3.0.1",
      "url-loader" -> "1.1.2"
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
      val indexFrom = baseDirectory.value / "assets/index.html"
      val indexTo = (Compile / fastOptJS / crossTarget).value / "index.html"
      Files.copy(indexFrom.toPath, indexTo.toPath, REPLACE_EXISTING)
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

      val indexFrom = baseDirectory.value / "assets/index.html"
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

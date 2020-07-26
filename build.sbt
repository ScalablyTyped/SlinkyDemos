import java.nio.file.Files
import java.nio.file.StandardCopyOption.REPLACE_EXISTING

import scala.sys.process.Process

Global / stRemoteCache := RemoteCache.Rsync(
  push = "tso@olvind.com:/usr/share/nginx/html/st-cache",
  pull = new java.net.URI("https://olvind.com/st-cache")
)

Global / onLoad := {
  println("""*
      |* Welcome to ScalablyTyped demos!
      |*
      |* These demos demonstrate how to use third party react components with Slinky.
      |*
      |* For documentation see https://scalablytyped.org .
      |*
      |* Note that the first time you import/compile the projects it'll take a while for the dependencies to build.
      |*
      |* If you import this the first time in a memory-constrained context like an IDE import, it'll take MUCH longer.
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
      scalaVersion := "2.13.2",
      scalacOptions ++= ScalacOptions.flags,
      scalaJSUseMainModuleInitializer := true,
      /* disabled because it somehow triggers many warnings */
      scalaJSLinkerConfig := scalaJSLinkerConfig.value.withSourceMap(false),
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
      Compile / npmDependencies ++= Seq(
        "mobx" -> "5.15.4",
        "mobx-react" -> "6.2.2"
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
    Compile / npmDependencies ++= Seq(
      "semantic-ui-react" -> "0.88.2"
    )
  )

/** Note: This can't use scalajs-bundler (at least I don't know how),
  *  so we run yarn ourselves with an external package.json.
  */
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
      Compile / npmDependencies ++= Seq("antd" -> "4.3.1")
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
    .configure(baseSettings, browserProject, reactNpmDeps, withCssLoading, bundlerSettings)
    .settings(
      useYarn := true,
      webpackDevServerPort := 8008,
      stFlavour := Flavour.Slinky,
      Compile / npmDependencies ++= Seq(
        "@material-ui/core" -> "3.9.4", // note: version 4 is not supported yet
        "@material-ui/styles" -> "3.0.0-alpha.10", // note: version 4 is not supported yet
        "@material-ui/icons" -> "3.0.2",
        "recharts" -> "1.8.5",
        "@types/recharts" -> "1.8.10",
        "@types/classnames" -> "2.2.10",
        "react-router-dom" -> "5.1.2",
        "@types/react-router-dom" -> "5.1.2"
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

lazy val `react-dnd` = project
  .enablePlugins(ScalablyTypedConverterPlugin)
  .configure(baseSettings, browserProject, reactNpmDeps, bundlerSettings)
  .settings(
    useYarn := true,
    webpackDevServerPort := 8011,
    stFlavour := Flavour.Slinky,
    Compile / npmDependencies ++= Seq(
      "react-dnd" -> "11.1.3",
      "react-dnd-html5-backend" -> "11.1.3"
    )
  )

lazy val `react-i18n` = project
  .enablePlugins(ScalablyTypedConverterPlugin)
  .configure(baseSettings, browserProject, reactNpmDeps, bundlerSettings)
  .settings(
    useYarn := true,
    webpackDevServerPort := 8012,
    stFlavour := Flavour.Slinky,
    Compile / npmDependencies ++= Seq(
      "i18next" -> "19.5.2",
      "i18next-browser-languagedetector" -> "5.0.0",
      "react-i18next" -> "11.7.0"
    )
  )

lazy val `nivo` = project
  .enablePlugins(ScalablyTypedConverterPlugin)
  .configure(baseSettings, browserProject, reactNpmDeps, bundlerSettings)
  .settings(
    useYarn := true,
    webpackDevServerPort := 8013,
    stFlavour := Flavour.Slinky,
    Compile / npmDependencies ++= Seq(
      "@nivo/line" -> "0.62.0"
    )
  )

/** Note: This can't use scalajs-bundler (at least I don't know how),
  *  so we run yarn ourselves with an external package.json.
  */
lazy val `react-native` = project
  .enablePlugins(ScalablyTypedConverterExternalNpmPlugin)
  .configure(baseSettings)
  .settings(
    scalaJSLinkerConfig := scalaJSLinkerConfig.value.withModuleKind(ModuleKind.CommonJSModule),
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

// specify versions for all of reacts dependencies to compile less since we have many demos here
lazy val reactNpmDeps: Project => Project =
  _.settings(
    stTypescriptVersion := "3.9.3",
    Compile / npmDependencies ++= Seq(
      "react" -> "16.13.1",
      "react-dom" -> "16.13.1",
      "@types/react" -> "16.9.42",
      "@types/react-dom" -> "16.9.8",
      "csstype" -> "2.6.11",
      "@types/prop-types" -> "15.7.3"
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

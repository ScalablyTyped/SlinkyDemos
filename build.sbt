import java.nio.file.Files
import java.nio.file.StandardCopyOption.REPLACE_EXISTING

import scala.sys.process.Process

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

lazy val `react-mobx` =
  project
    .configure(baseSettings, bundlerSettings, browserProject)
    .settings(
      webpackDevServerPort := 8001,
      libraryDependencies ++= Seq(
        SlinkyTyped.A.axios,
        SlinkyTyped.M.`material-ui`,
        SlinkyTyped.M.mobx,
        SlinkyTyped.M.`mobx-react`,
        SlinkyTyped.R.`react-dom`
      ),
      Compile / npmDependencies ++= Seq(
        "material-ui" -> "0.20.1",
        "react" -> "16.9",
        "react-dom" -> "16.9"
      )
    )

lazy val `react-slick` =
  project
    .configure(baseSettings, bundlerSettings, browserProject)
    .settings(
      webpackDevServerPort := 8005,
      libraryDependencies ++= Seq(
        SlinkyTyped.R.`react-dom`,
        SlinkyTyped.R.`react-slick`
      ),
      Compile / npmDependencies ++= Seq(
        "react" -> "16.9",
        "react-dom" -> "16.9",
        "react-slick" -> "0.23"
      )
    )

lazy val `react-big-calendar` =
  project
    .configure(baseSettings, bundlerSettings, browserProject, withCssLoading)
    .settings(
      webpackDevServerPort := 8007,
      libraryDependencies ++= Seq(
        SlinkyTyped.M.moment,
        SlinkyTyped.R.`react-dom`,
        SlinkyTyped.R.`react-big-calendar`
      ),
      Compile / npmDependencies ++= Seq(
        "react" -> "16.9",
        "react-dom" -> "16.9",
        "react-big-calendar" -> "0.22"
      )
    )

lazy val `semantic-ui-react` = project
  .configure(baseSettings, bundlerSettings, browserProject)
  .settings(
    webpackDevServerPort := 8009,
    libraryDependencies ++= Seq(
      SlinkyTyped.R.`redux-devtools-extension`,
      SlinkyTyped.R.`react-dom`,
      SlinkyTyped.R.`react-redux`,
      SlinkyTyped.S.`semantic-ui-react`,
    ),
    Compile / npmDependencies ++= Seq(
      "react-dom" -> "16.9",
      "react" -> "16.9",
      "react-redux" -> "7.1",
    ),
  )


lazy val `storybook-react` = project
  .configure(baseSettings, application)
  .settings(
    libraryDependencies ++= Seq(
      SlinkyTyped.N.node,
      SlinkyTyped.R.react,
      SlinkyTyped.S.storybook__react,
    ),
    /** This is not suitable for development, but effective for demo.
      * Run `yarn` and `yarn storybook` commands yourself, and run `~storybook-react/fastOptJS` from sbt
      */
    run := {
      Process("yarn", baseDirectory.value).!
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
//
//lazy val `material-ui` =
//  project
//    .configure(baseSettings, bundlerSettings, browserProject)
//    .settings(
//      webpackDevServerPort := 8016,
//      libraryDependencies ++= Seq(
//        SlinkyTyped.M.`material-ui__core`,
//        SlinkyTyped.M.`material-ui__icons`,
//        SlinkyTyped.R.`react-facade`,
//        SlinkyTyped.R.`react-dom`,
//      ),
//      Compile / npmDependencies ++= Seq(
//        "react" -> "16.9",
//        "react-dom" -> "16.9",
//      )
//    )

lazy val `antd-slinky` =
  project
    .configure(baseSettings, bundlerSettings, browserProject, withCssLoading)
    .settings(
      webpackDevServerPort := 8018,
      libraryDependencies ++= Seq(
        SlinkyTyped.R.`react-dom`,
        SlinkyTyped.A.antd,
      ),
      Compile / npmDependencies ++= Seq(
        "react" -> "16.9",
        "react-dom" -> "16.9",
      )
    )

lazy val `react-router-dom-slinky` =
  project
    .configure(baseSettings, bundlerSettings, browserProject)
    .settings(
      webpackDevServerPort := 8020,
      libraryDependencies ++= Seq(SlinkyTyped.R.`react-router-dom`),
      Compile / npmDependencies ++= Seq(
        "react" -> "16.9",
        "react-dom" -> "16.9",
        "react-router-dom" -> "5.0.0",
      )
    )

// todo: disabled since they changed everything in react-navigation and I'm lazy.
//  We'll wait for the next version or replace it altogether.
//
//lazy val `react-native` = project
//  .configure(baseSettings, outputModule, application)
//  .settings(
//    scalaJSUseMainModuleInitializer := false,
//    libraryDependencies ++= Seq(
//      SlinkyTyped.E.`expo-font`,
//      SlinkyTyped.R.`react-native`,
//      SlinkyTyped.R.`react-navigation`,
//      SlinkyTyped.R.`react-native-gesture-handler`,
//      SlinkyTyped.R.`react-native-vector-icons`,
//      SlinkyTyped.R.`react-facade`
//    ),
//  )

lazy val baseSettings: Project => Project =
  _.enablePlugins(ScalaJSPlugin)
    .settings(
      addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full),
      scalaVersion := "2.12.9",
      version := "0.1-SNAPSHOT",
      scalacOptions ++= ScalacOptions.flags,
      /* in preparation for scala.js 1.0 */
      scalacOptions += "-P:scalajs:sjsDefinedByDefault"
    )

lazy val application: Project => Project =
  _.settings(
    scalaJSUseMainModuleInitializer := true,
    /* disabled because it somehow triggers many warnings */
    emitSourceMaps := false,
    scalaJSModuleKind := ModuleKind.CommonJSModule
  )

lazy val bundlerSettings: Project => Project =
  _.enablePlugins(ScalaJSBundlerPlugin)
    .configure(application)
    .settings(
      /* Specify current versions and modes */
      startWebpackDevServer / version := "3.1.10",
      webpack / version := "4.26.1",
      Compile / fastOptJS / webpackExtraArgs += "--mode=development",
      Compile / fullOptJS / webpackExtraArgs += "--mode=production",
      Compile / fastOptJS / webpackDevServerExtraArgs += "--mode=development",
      Compile / fullOptJS / webpackDevServerExtraArgs += "--mode=production",
      useYarn := true
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
      val indexTo   = (Compile / fastOptJS / crossTarget).value / "index.html"
      Files.copy(indexFrom.toPath, indexTo.toPath, REPLACE_EXISTING)
    },
    dist := {
      val artifacts      = (Compile / fullOptJS / webpack).value
      val artifactFolder = (Compile / fullOptJS / crossTarget).value
      val distFolder     = (ThisBuild / baseDirectory).value / "docs" / moduleName.value

      distFolder.mkdirs()
      artifacts.foreach { artifact =>
        val target = artifact.data.relativeTo(artifactFolder) match {
          case None          => distFolder / artifact.data.name
          case Some(relFile) => distFolder / relFile.toString
        }

        Files.copy(artifact.data.toPath, target.toPath, REPLACE_EXISTING)
      }

      val indexFrom = baseDirectory.value / "assets/index.html"
      val indexTo   = distFolder / "index.html"

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

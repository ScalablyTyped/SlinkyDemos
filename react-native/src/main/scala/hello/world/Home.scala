package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.native.{Image, Text, View}
import typings.antDesignReactNative.components.Button
import typings.antDesignReactNative.{antDesignReactNativeStrings => antdStrings}
import typings.expoConstants.mod.default.{expoVersion, statusBarHeight}
import typings.expoLinking.mod.openURL
import typings.expoWebBrowser.mod.openBrowserAsync
import typings.reactNative.mod.{TextStyle, ViewStyle}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("../../assets/scala-logo.png", JSImport.Default)
@js.native
object ScalaLogo extends js.Object

@react object Home {
  type Props = Unit

  val component = FunctionalComponent[Props] {
    case () =>

      def linking = openURL("https://scalablytyped.org").toFuture
      def webBrowser = openBrowserAsync("https://expo.io").toFuture

      View(style = ViewStyle().setMargin(20))(
        Text(style = TextStyle().setFontSize(16))(
          "This is a demo written in Scala through Scala.js, Slinky, ScalablyTyped and Expo.\n\n" +
            "It uses components from Antd Native and React Router Native."
        ),
        Text(s"The status bar is ${statusBarHeight} pixels high."),
        Text(s"Expo version is ${expoVersion}."),
        Button("Open URL with ReactNative.Linking")
          .`type`(antdStrings.primary)
          .onPress(_ => linking.foreach(_ => ())),
        Button("Open URL with Expo.WebBrowser")
          .`type`(antdStrings.primary)
          .onPress(_ => webBrowser.foreach(_ => ())),
        Text(s"This image is a local asset:"),
        Image(source = ScalaLogo)
      )
  }
}

package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import typings.reactNative.mod.ViewStyle
import typings.reactNativeWebview.components.WebView
import typings.reactNativeWebview.webViewTypesMod.WebViewSourceUri

@react object Webview {

  type Props = Unit

  val component = FunctionalComponent[Props] {
    case () =>
      WebView.style(ViewStyle().setMarginTop(5)).source(WebViewSourceUri("https://scalablytyped.org"))
  }
}

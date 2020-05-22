package hello.world

import typings.reactNative.mod.TextStyle
import typings.reactNative.reactNativeStrings

object Styles {
  val headerStyle = TextStyle()
    .setPadding(10)
    .setFontSize(20)
    .setColor("black")

  val topicStyle = TextStyle()
    .setPaddingTop(30)
    .setTextAlign(reactNativeStrings.center)
    .setFontSize(15)

  val subNavItemStyle = TextStyle().setPadding(5)

  val title = TextStyle()
    .setPadding(10)
    .setFontSize(20)
    .setFontWeight(reactNativeStrings.bold)
    .setTextAlign(reactNativeStrings.center)
    .setColor("red")

}

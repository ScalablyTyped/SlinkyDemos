package hello.world

import typings.expoConstants.mod.default.statusBarHeight
import typings.reactNative.mod.{FlexAlignType, TextStyle, ViewStyle}
import typings.reactNative.reactNativeStrings

object Styles {

  val container: ViewStyle = ViewStyle()
    .setFlex(1)
    .setMarginTop(statusBarHeight)

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

  val antdInput = ViewStyle()
    .setBackgroundColor("white")
    .setFlex(1)
    .setFlexDirection(reactNativeStrings.column)
    .setJustifyContent(reactNativeStrings.center)
    .setAlignItems(FlexAlignType.center)

}

package hello.world

import typingsSlinky.reactNative.mod.TextStyle
import typingsSlinky.reactNative.reactNativeStrings

import scala.scalajs.js.Dynamic.literal

object Styles {
  val headerStyle = TextStyle(
    padding = 10,
    fontSize = 20,
    color = "black"
  )

  val topicStyle = TextStyle(
    paddingTop = 30,
    textAlign = reactNativeStrings.center,
    fontSize = 15
  )

  val subNavItemStyle = literal(
    padding = 5
  )

  val title = TextStyle(
    padding = 10,
    fontSize = 20,
    fontWeight = reactNativeStrings.bold,
    textAlign = reactNativeStrings.center,
    color = "red"
  )
}

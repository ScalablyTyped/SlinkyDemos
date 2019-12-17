package hello.world

import typingsSlinky.reactDashNative.reactDashNativeMod.TextStyle
import typingsSlinky.reactDashNative.reactDashNativeStrings
import scala.scalajs.js
import scala.scalajs.js.Dynamic.literal

object Styles {
  val headerStyle = literal(
    padding = 10,
    fontSize = 20,
    color = "black"
  )

  val topicStyle = literal(
    paddingTop = 30,
    textAlign = "center",
    fontSize = 15
  )

  val subNavItemStyle = literal(
    padding = 5
  )

  val title = TextStyle(
    padding = 10,
    fontSize = 20,
    fontWeight = reactDashNativeStrings.bold,
    textAlign = reactDashNativeStrings.center,
    color = "red"
  )
}

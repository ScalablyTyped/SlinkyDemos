package hello.world

import slinky.core._
import slinky.core.annotations.react

import slinky.native._

import scala.scalajs.js
import scala.scalajs.js.Dynamic.literal
import scala.scalajs.js.annotation.{JSImport, ScalaJSDefined}

@react class App extends Component {
  type Props = Unit
  type State = Int

  def initialState = 0

  def render() = {
    View(
      style = literal(
        backgroundColor = "white",
        padding = 50,
        flex = 1,
        flexDirection = "column",
        justifyContent = "center",
        alignItems = "center"
      )
    )(
      Image(
        source = ImageURISource(
          uri = "https://raw.githubusercontent.com/shadaj/slinky/master/logo.png"
        ),
        style = literal(
          width = 250,
          height = 250
        ),
        resizeMode = "cover"
      ),
      Text(
        style = literal(fontSize = 30, color = "red")
      )("Hello, Slinky Native!"),
      Text("Count: " + state),
      Button(title = "Tap Me!", onPress = () => {
        setState(_ + 1)
      })
    )
  }
}

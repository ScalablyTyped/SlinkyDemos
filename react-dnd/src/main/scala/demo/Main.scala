package demo

import org.scalajs.dom
import slinky.web.ReactDOM

object Main {
  def main(args: Array[String]): Unit =
    ReactDOM.render(App(), dom.document.getElementById("container"))
}

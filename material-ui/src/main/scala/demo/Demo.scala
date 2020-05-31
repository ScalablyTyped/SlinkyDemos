package demo

import demo.dashboard.Dashboard
import org.scalajs.dom
import slinky.web.ReactDOM

object Demo {

  def main(argv: Array[String]): Unit =
    ReactDOM.render(
      Dashboard(()),
      dom.document.getElementById("container")
    )
}

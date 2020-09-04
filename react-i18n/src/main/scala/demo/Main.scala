package demo

import org.scalajs.dom
import slinky.web.ReactDOM

object Main {
  def main(argv: Array[String]): Unit = {

    val container = Option(dom.document.getElementById("root")).getOrElse {
      val elem = dom.document.createElement("div")
      elem.id = "root"
      dom.document.body.appendChild(elem)
      elem
    }

    I18n.initialize()
    ReactDOM.render(
      App(),
      container
    )
  }
}

package demo

import org.scalajs.dom.document
import slinky.web.ReactDOM

object Main {
  def main(argv: Array[String]): Unit = {
    I18n.default
    ReactDOM.render(
      App(),
      document.getElementsByTagName("body")(0)
    )
  }
}

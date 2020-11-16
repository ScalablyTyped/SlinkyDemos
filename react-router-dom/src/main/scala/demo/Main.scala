package demo

import org.scalajs.dom
import slinky.hot
import slinky.web.ReactDOM

import scala.scalajs.LinkingInfo

object Main {
  def main(args: Array[String]): Unit = {
    if (LinkingInfo.developmentMode)
      hot.initialize()
    val container = Option(dom.document.getElementById("root")).getOrElse {
      val elem = dom.document.createElement("div")
      elem.id = "root"
      dom.document.body.appendChild(elem)
      elem
    }

    ReactDOM.render(App.component(()), container)
  }
}

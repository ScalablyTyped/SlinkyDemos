package demo

import org.scalajs.dom
import slinky.web.ReactDOM

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Main {
  def main(args: Array[String]): Unit = {
    IndexCSS
    ReactDOM.render(App.component(()), dom.document.getElementById("container"))
  }
}

@JSImport("../../../../src/main/resources/index.css", JSImport.Namespace)
@js.native
object IndexCSS extends js.Object

package demo

import org.scalajs.dom.document
import slinky.web.ReactDOM
import typings.nivoLine.mod.Serie
import typings.std.global.console

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Main {
  @js.native @JSImport("./data.json", JSImport.Namespace)
  val Data: js.Array[Serie] = js.native

  def main(argv: Array[String]): Unit = {
    console.warn(Data)
    ReactDOM.render(
      App(Data),
      document.getElementById("container")
    )
  }
}

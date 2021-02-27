package demo

import org.scalajs.dom
import slinky.core.FunctionalComponent
import slinky.web.ReactDOM
import typings.cytoscape.mod._
import typings.react.mod.CSSProperties
import typings.reactCytoscapejs.components.ReactCytoscapejs

import scala.scalajs.js

object Demo {
  val CytoscapeDemo = FunctionalComponent[Unit] {
    case () =>
      ReactCytoscapejs(elements =
        js.Array(
          ElementDefinition(NodeDataDefinition().setId("a")).setPosition(Position(200, 100)),
          ElementDefinition(NodeDataDefinition().setId("b")).setPosition(Position(300, 200)),
          ElementDefinition(EdgeDataDefinition("a", "b"))
        )
      ).style(CSSProperties().setWidth("600px").setHeight("600px"))
  }

  def main(args: Array[String]): Unit =
    ReactDOM.render(CytoscapeDemo(()), dom.document.getElementById("container"))
}

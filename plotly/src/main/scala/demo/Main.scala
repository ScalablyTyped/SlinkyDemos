package demo

import org.scalajs.dom.document
import slinky.core.FunctionalComponent
import slinky.web.ReactDOM
import typings.plotlyJs.anon.{PartialPlotData, PartialPlotDataAutobinx, PartialPlotMarker, PartialPlotMarkerAutocolorscale, PartialPlotMarkerCauto}
import typings.plotlyJs.mod.{Data, PlotType}
import typings.plotlyJs.plotlyJsStrings
import typings.reactPlotlyJs.anon.PartialLayout
import typings.reactPlotlyJs.components.ReactPlotlyDotjs

import scala.scalajs.js

object Main {
  val Component = FunctionalComponent[Unit] {
    case () =>
      val data = js.Array[Data](
        PartialPlotData()
          .setXVarargs(1, 2, 3)
          .setYVarargs(2, 6, 3)
          .setType(PlotType.scatter)
          .setMode(plotlyJsStrings.linesPlussignmarkers)
          .setMarker(
            PartialPlotMarker()
              .setColor("red")
          ),
        PartialPlotData()
          .setType(PlotType.bar)
          .setXVarargs(1, 2, 3)
          .setYVarargs(2, 5, 3)
      )

      ReactPlotlyDotjs(data = data, layout = PartialLayout().setWidth(320).setHeight(240).setTitle("A Fancy Plot")).debug(true)
  }

  def main(argv: Array[String]): Unit =
    ReactDOM.render(Component(()), document.body)
}

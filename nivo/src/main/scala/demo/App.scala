package demo

import org.scalablytyped.runtime.StringDictionary
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import typings.nivoAxes.mod.{AxisProps, Orient}
import typings.nivoAxes.nivoAxesStrings.middle
import typings.nivoColors.mod.{ColorSchemeId, OrdinalColorsInstruction, SchemeColorInstruction}
import typings.nivoCore.mod.Box
import typings.nivoLegends.anon.PartialitemTextColorstrin
import typings.nivoLegends.mod._
import typings.nivoLine.components.Line
import typings.nivoLine.mod.Serie
import typings.nivoScales.mod.{LinearScale, Scale}
import typings.nivoScales.nivoScalesStrings.auto

import scala.scalajs.js

// ported from https://nivo.rocks/line
@react
object App {
  case class Props(data: js.Array[Serie])

  //// make sure parent container have a defined height when using
  //// responsive component, otherwise height will be 0 and
  //// no chart will be rendered.
  //// website examples showcase many properties,
  //// you'll often use just a few of them.
  val component = FunctionalComponent[Props] {
    case Props(data) =>
      Line(data, height = 1000, width = 1500)
        .margin(Box().setTop(50).setRight(110).setBottom(50).setLeft(60))
        .xScale(Scale.PointScale())
        .yScale(LinearScale().setMin(auto).setMax(auto).setStacked(true).setReverse(false))
        .axisTopNull
        .axisRightNull
        .axisBottom(
          AxisProps()
            .setOrient(Orient.bottom)
            .setTickSize(5)
            .setTickPadding(5)
            .setTickRotation(0)
            .setLegend("transportation")
            .setLegendOffset(36)
            .setLegendPosition(middle)
        )
        .axisLeft(
          AxisProps()
            .setOrient(Orient.left)
            .setTickSize(5)
            .setTickPadding(5)
            .setTickRotation(0)
            .setLegend("count")
            .setLegendOffset(-40)
            .setLegendPosition(middle)
        )
        .colors(SchemeColorInstruction(ColorSchemeId.nivo))
        .pointSize(10)
        .pointColor(StringDictionary("theme" -> "background"))
        .pointBorderWidth(2)
        .pointBorderColor(StringDictionary("from" -> "serieColor"))
        .pointLabel("y")
        .pointLabelYOffset(-12)
        .useMesh(true)
        .legendsVarargs(
          LegendProps(
            anchor = LegendAnchor.`bottom-right`,
            direction = LegendDirection.column,
            itemHeight = 20,
            itemWidth = 80
          ).setJustify(false)
            .setTranslateX(100)
            .setTranslateY(0)
            .setItemsSpacing(0)
            .setItemDirection(LegendItemDirection.`left-to-right`)
            .setItemOpacity(0.75)
            .setSymbolSize(12)
            .setSymbolShape(LegendSymbolShape.circle)
            .setSymbolBorderColor("rgba(0, 0, 0, .5)")
            .setEffectsVarargs(
              LegendEffect(PartialitemTextColorstrin().setItemBackground("rgba(0, 0, 0, .03)").setItemOpacity(1))
            )
        )
  }
}

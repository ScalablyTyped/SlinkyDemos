package demo.dashboard

import demo.StyleBuilder
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.web.html._
import typings.csstype.csstypeStrings.auto
import typings.materialUiCore.components._
import typings.materialUiCore.createMuiThemeMod.Theme
import typings.materialUiCore.materialUiCoreStrings.right
import typings.materialUiStyles.withStylesMod.CSSProperties

import scala.scalajs.js

// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/SimpleTable.js
@react object SimpleTable {

  lazy val styles =
    StyleBuilder[Theme, js.Object]
      .add("root", CSSProperties().setWidth("100%").setOverflowX(auto))
      .add("table", CSSProperties().setMinWidth(700))
      .hook

  case class Data(id: Long, name: String, calories: Double, fat: Double, carbs: Double, protein: Double)

  val data = Seq(
    Data(1, "Frozen yoghurt", 159, 6.0, 24, 4.0),
    Data(2, "Ice cream sandwich", 237, 9.0, 37, 4.3),
    Data(3, "Eclair", 262, 16.0, 24, 6.0),
    Data(4, "Cupcake", 305, 3.7, 67, 4.3),
    Data(5, "Gingerbread", 356, 16.0, 49, 3.9)
  )

  type Props = Unit

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] {
    case () =>
      val classes = styles(js.undefined)
      Paper.className(classes("root"))(
        Table(className := classes("table"))(
          TableHead(
            TableRow(
              TableCell("Dessert (100g serving)"),
              TableCell.align(right)("Calories"),
              TableCell.align(right)("Fat (g)"),
              TableCell.align(right)("Carbs (g)"),
              TableCell.align(right)("Protein (g)")
            )
          ),
          TableBody(
            data.map { n =>
              TableRow.withKey(n.id.toString)(
                TableCell.set("component", "th").scope("row")(n.name),
                TableCell.align(right)(n.calories),
                TableCell.align(right)(n.fat),
                TableCell.align(right)(n.carbs),
                TableCell.align(right)(n.protein)
              )
            }
          )
        )
      )
  }
}

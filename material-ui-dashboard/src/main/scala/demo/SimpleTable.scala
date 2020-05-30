package demo

import org.scalablytyped.runtime.StringDictionary
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.web.html._
import typings.csstype.csstypeStrings.auto
import typings.materialUiCore.createMuiThemeMod.Theme
import typings.materialUiCore.materialUiCoreStrings.right
import typings.materialUiCore.{ components => Mui }
import typings.materialUiStyles.makeStylesMod.StylesHook
import typings.materialUiStyles.mod.makeStyles
import typings.materialUiStyles.withStylesMod.{ CSSProperties, StyleRulesCallback, WithStylesOptions }

import scala.scalajs.js

// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/SimpleTable.js
@react object SimpleTable {

  lazy val styles: StylesHook[StyleRulesCallback[Theme, js.Object, String]] = {
    lazy val styles: StyleRulesCallback[Theme, js.Object, String] = theme =>
      StringDictionary(
        "root" -> CSSProperties()
          .setMinWidth("100%")
          .setMaxWidth("100%")
          .setOverflowX(auto),
        "table" -> CSSProperties()
          .setMinWidth(700)
      )

    makeStyles[StyleRulesCallback[Theme, js.Object, String]](styles, WithStylesOptions())
  }

  case class Data(id: Long, name: String, calories: Double, fat: Double, carbs: Double, protein: Double)

  val data = Seq(
    Data(1, "Frozen yoghurt", 159, 6.0, 24, 4.0),
    Data(2, "Ice cream sandwich", 237, 9.0, 37, 4.3),
    Data(3, "Eclair", 262, 16.0, 24, 6.0),
    Data(4, "Cupcake", 305, 3.7, 67, 4.3),
    Data(5, "Gingerbread", 356, 16.0, 49, 3.9)
  )

  val component: FunctionalComponent[Unit] = FunctionalComponent[Unit] {
    case () =>
      val classes = styles()
      Mui.Paper.className(classes("root"))(
        Mui.Table(className := classes("table"))(
          Mui.TableHead()(
            Mui.TableRow()(
              Mui.TableCell("Dessert (100g serving)"),
              Mui.TableCell.align(right)("Calories"),
              Mui.TableCell.align(right)("Fat (g)"),
              Mui.TableCell.align(right)("Carbs (g)"),
              Mui.TableCell.align(right)("Protein (g)")
            )
          ),
          Mui.TableBody()(
            data.map { n =>
              Mui.TableRow.withKey(n.id.toString)(
                Mui.TableCell.set("component", "th".asInstanceOf[js.Any]).scope("row")(n.name),
                Mui.TableCell.align(right)(n.calories),
                Mui.TableCell.align(right)(n.fat),
                Mui.TableCell.align(right)(n.carbs),
                Mui.TableCell.align(right)(n.protein)
              )
            }
          )
        )
      )
  }

}

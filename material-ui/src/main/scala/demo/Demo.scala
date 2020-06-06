package demo

import demo.dashboard.Dashboard
import org.scalajs.dom
import slinky.web.ReactDOM
import typings.materialUiCore.createMuiThemeMod.{Theme, ThemeOptions}
import typings.materialUiCore.spacingMod.SpacingOptions
import typings.materialUiCore.stylesMod
import typings.materialUiStyles.components.ThemeProvider
import typings.materialUiCore.stylesMod.createMuiTheme

object Demo {

  val theme: Theme = stylesMod.createMuiTheme(ThemeOptions().setSpacing(SpacingOptions().setUnit(2)))

  def main(argv: Array[String]): Unit =
    ReactDOM.render(
      ThemeProvider(theme)(Dashboard()),
      dom.document.getElementById("container")
    )
}

package demo

import demo.dashboard.Dashboard
import org.scalajs.dom
import slinky.web.ReactDOM
import typings.materialUiStyles.components.ThemeProvider
import typings.materialUiCore.stylesMod.createMuiTheme

object Demo {
  val theme = createMuiTheme
  def main(argv: Array[String]): Unit =
    ReactDOM.render(
      ThemeProvider(theme)(Dashboard(())),
      dom.document.getElementById("container")
    )
}

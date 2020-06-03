package demo

import demo.album.Album
import demo.button.{ButtonTest, SelectDemo, StyledButtonDemo, StyledButtonHooksDemo}
import demo.dashboard.Dashboard
import org.scalajs.dom
import slinky.core.FunctionalComponent
import slinky.core.facade.Fragment
import slinky.web.ReactDOM
import typings.materialUiCore.components.{List, ListItem, ListItemIcon, ListItemText, ListSubheader, Typography}
import typings.materialUiCore.createMuiThemeMod.{Theme, ThemeOptions}
import typings.materialUiCore.spacingMod.SpacingOptions
import typings.materialUiCore.stylesMod.createMuiTheme
import typings.materialUiCore.typographyTypographyMod.Style
import typings.materialUiIcons.{components => Icon}
import typings.materialUiStyles.components.ThemeProvider
import typings.reactRouter.mod.RouteProps
import typings.reactRouterDom.components.{BrowserRouter, Link, Route}

object Demo {
  val theme: Theme = createMuiTheme(ThemeOptions().setSpacing(SpacingOptions().setUnit(2)))

  type Props = Unit

  val Main: FunctionalComponent[Props] = FunctionalComponent[Props] {
    case () =>
      ThemeProvider(theme)(
        BrowserRouter(
          Route(
            RouteProps()
              .setExact(true)
              .setPath("/")
              .setRender(_ =>
                List(
                  ListSubheader.inset(true)(""),
                  Link[String](to = "/dashboard")(
                    ListItem.button(true)(ListItemIcon(Icon.Assignment()), ListItemText.primary("Dashboard"))
                  ),
                  Link[String](to = "/album")(
                    ListItem.button(true)(ListItemIcon(Icon.Assignment()), ListItemText.primary("Album"))
                  ),
                  Link[String](to = "/button")(
                    ListItem.button(true)(ListItemIcon(Icon.Assignment()), ListItemText.primary("Buttons"))
                  ),
                  Link[String](to = "/select")(
                    ListItem.button(true)(ListItemIcon(Icon.Assignment()), ListItemText.primary("Select"))
                  )
                )
              )
          ),
          Route(RouteProps().setPath("/dashboard").setRender(_ => Dashboard())),
          Route(
            RouteProps()
              .setPath("/album")
              .setRender(_ =>
                Fragment(
                  Typography().variant(Style.h4).gutterBottom(true).component("h2")("Album"),
                  Album()
                )
              )
          ),
          Route(
            RouteProps()
              .setPath("/button")
              .setRender(_ =>
                Fragment(
                  Typography.variant(Style.h4).gutterBottom(true).component("h2")("Buttons"),
                  ButtonTest("dear user"),
                  StyledButtonDemo(),
                  StyledButtonHooksDemo()
                )
              )
          ),
          Route(
            RouteProps()
              .setPath("/select")
              .setRender(_ =>
                Fragment(
                  Typography.variant(Style.h4).gutterBottom(true).component("h2")("Select"),
                  SelectDemo(scala.List("one", "two", "three"))
                )
              )
          )
        )
      )
  }

  def main(argv: Array[String]): Unit =
    ReactDOM.render(
      Main(()),
      dom.document.getElementById("container")
    )
}

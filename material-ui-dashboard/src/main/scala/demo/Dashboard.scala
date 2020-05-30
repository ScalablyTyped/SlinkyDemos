package demo

import org.scalablytyped.runtime.StringDictionary
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks
import slinky.web.html._
import typings.materialUiCore.createMuiThemeMod.Theme
import typings.materialUiCore.materialUiCoreStrings.{absolute, permanent}
import typings.materialUiCore.mod.PropTypes.Color
import typings.materialUiCore.typographyTypographyMod.Style
import typings.materialUiCore.{components => Mui}
import typings.materialUiIcons.components.{Menu, Notifications}
import typings.materialUiStyles.makeStylesMod.StylesHook
import typings.materialUiStyles.mod.makeStyles
import typings.classnames
import typings.materialUiStyles.withStylesMod.{CSSProperties, StyleRulesCallback, WithStylesOptions}

import scala.scalajs.js

// https://v3.material-ui.com/getting-started/page-layout-examples/dashboard/
// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/Dashboard.js
@react object Dashboard {

  lazy val styles: StylesHook[StyleRulesCallback[Theme, js.Object, String]] = {
    lazy val styles: StyleRulesCallback[Theme, js.Object, String] = theme =>
      StringDictionary(
        "root" -> CSSProperties()
          .setDisplay(typings.csstype.csstypeStrings.flex),
        "toolbar" -> CSSProperties()
          .setPaddingRight(24),
        "appBar" -> CSSProperties(),
        "menuButton" -> CSSProperties()
          .setMarginLeft(12)
          .setMarginRight(36),
        "title" -> CSSProperties()
          .setFlexGrow(1),
      )

    makeStyles[StyleRulesCallback[Theme, js.Object, String]](styles, WithStylesOptions())
  }

  val component: FunctionalComponent[Unit] = FunctionalComponent[Unit] {
    case () =>
      val (state, setState) = Hooks.useState(true)
      val classes = styles()
      div()(
        Mui.CssBaseline(),
        Mui.AppBar
          .position(absolute)
          .className(classes("appBar"))(
            Mui.Toolbar.disableGutters(!state)
              .className(classes("toolbar"))(
                Mui.IconButton
                  .color(Color.inherit)
                  .`aria-label`("Open drawer")
                  .className(classes("menuButton"))(
                    Menu()
                  ),
                Mui.Typography(h1()).variant(Style.h6).color(Color.inherit).noWrap(true).className(classes("title"))("Dashboard"),
                Mui.IconButton.color(Color.inherit)(
                  Mui.Badge
                    .badgeContent(4)
                    .color(Color.secondary)(
                      Notifications()
                    )
                )
              )
          ),
        Mui.Drawer.variant(permanent)(
          Mui.List(ListItems.mainListItems),
          Mui.List(ListItems.secondaryListItems)
        )
      )
  }

}

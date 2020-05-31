package demo.dashboard

import org.scalablytyped.runtime.StringDictionary
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks
import slinky.web.html._
import typings.csstype.csstypeStrings.{hidden => _, _}
import typings.csstype.mod.OverflowXProperty
import typings.materialUiCore.anon.Partialdurationnumberstri
import typings.materialUiCore.createMuiThemeMod.Theme
import typings.materialUiCore.materialUiCoreStrings.{absolute, permanent}
import typings.materialUiCore.mod.PropTypes.Color
import typings.materialUiCore.typographyTypographyMod.Style
import typings.materialUiCore.{components => Mui}
import typings.materialUiIcons.components.{ChevronLeft, Menu, Notifications}
import typings.materialUiStyles.makeStylesMod.StylesHook
import typings.materialUiStyles.mod.makeStyles
import typings.materialUiStyles.withStylesMod.{CSSProperties, StyleRulesCallback, WithStylesOptions}

import scala.scalajs.js
// https://v3.material-ui.com/getting-started/page-layout-examples/dashboard/
// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/Dashboard.js
@react object Dashboard {

  val drawerWidth = 240

  lazy val styles: StylesHook[StyleRulesCallback[Theme, js.Object, String]] = {
    lazy val styles: StyleRulesCallback[Theme, js.Object, String] = theme =>
      StringDictionary(
        "root" -> CSSProperties()
          .setDisplay(flex),
        "toolbar" -> CSSProperties()
          .setPaddingRight(24),
        "toolbarIcon" -> CSSProperties()
          .setDisplay(flex)
          .setAlignItems(center)
          .setJustifyContent(`flex-end`)
          .setPadding("0 8px")
        // TODO https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/Dashboard.js#L35
        ,
        "appBar" -> CSSProperties()
          // TODO zIndex ?¿ https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/Dashboard.js#L38
          .setTransition(theme.transitions.create(js.Array("width", "margin"),
            Partialdurationnumberstri().setEasing(theme.transitions.easing.sharp)
              .setDuration(theme.transitions.duration.enteringScreen)))
        ,
        "appBarShift" -> CSSProperties()
          .setMarginLeft(drawerWidth)
          //.setMaxWidth() TODO https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/Dashboard.js#L46
          //.setMinWidth()
          .setTransition(theme.transitions.create(js.Array("width", "margin"),
            Partialdurationnumberstri().setEasing(theme.transitions.easing.sharp)
              .setDuration(theme.transitions.duration.enteringScreen)))
        ,
        "menuButton" -> CSSProperties()
          .setMarginLeft(12)
          .setMarginRight(36),
        "menuButtonHidden" -> CSSProperties()
          .setDisplay(none)
          .setMarginRight(36),
        "title" -> CSSProperties()
          .setFlexGrow(1),
        "drawerPaper" -> CSSProperties()
          .setPosition(relative)
          //.setWhiteSpace(noWrap) TODO https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/Dashboard.js#L64
          //.setMaxWidth(),
          //.setMinWidth(),
          .setTransition(theme.transitions.create("width",
            Partialdurationnumberstri().setEasing(theme.transitions.easing.sharp)
              .setDuration(theme.transitions.duration.enteringScreen))),
        "drawerPaperClose" -> CSSProperties()
          .setOverflowX(OverflowXProperty.hidden)
          .setTransition(theme.transitions.create("width",
            Partialdurationnumberstri().setEasing(theme.transitions.easing.sharp)
              .setDuration(theme.transitions.duration.enteringScreen))),
        //.setMaxWidth(theme.spacing.unit * 7)
        //.setMinWidth(theme.spacing.unit * 7)
        //.set(theme.breakpoints.up(sm), ) TODO https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/Dashboard.js#L77
        //"appBarSpacer" -> theme.mixins.toolbar, // TODO
        "content" -> CSSProperties()
          .setFlexGrow(1)
          .setPadding(theme.spacing.unit * 3)
          .setHeight("100vh")
          .setOverflow(auto),
        "charContainer" -> CSSProperties()
          .setMarginLeft(-22),
        "tableContainer" -> CSSProperties()
          .setHeight(320),
        "h5" -> CSSProperties()
          .setMarginBottom(theme.spacing.unit * 2),
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
          .className(classes("appBar")) // TODO https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/Dashboard.js#L121
          (
            Mui.Toolbar
              .disableGutters(!state) // TODO https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/Dashboard.js#L123
              .className(classes("toolbar"))(
                Mui.IconButton
                  .color(Color.inherit)
                  .`aria-label`("Open drawer")
                  .className(classes("menuButton"))(
                    Menu()
                  ),
                Mui.Typography(h1())
                  .variant(Style.h6)
                  .color(Color.inherit)
                  .noWrap(true)
                  .className(classes("title"))("Dashboard"),
                Mui.IconButton.color(Color.inherit)(
                  Mui.Badge
                    .badgeContent(4)
                    .color(Color.secondary)(
                      Notifications()
                    )
                )
              )
          ),
        Mui.Drawer
          .variant(permanent)
          //.classes() TODO https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/Dashboard.js#L153
          (
            div()(
              Mui.IconButton()(
                ChevronLeft()
              )
            ),
            // Mui.Divider(), TODO this doesn't work
            Mui.List(ListItems.mainListItems),
            // Mui.Divider(), TODO this doesn't work
            Mui.List(ListItems.secondaryListItems)
          ),
        div(className := classes("content"))(
          div(className := classes("appBarSpacer"))(),
          Mui.Typography()
            .variant(Style.h4)
            .gutterBottom(true)
            //.component() TODO https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/Dashboard.js#L176
            ("Products"),
          div(className := classes("tableContainer"))(
            SimpleTable {}
          )
        )
      )
  }

}

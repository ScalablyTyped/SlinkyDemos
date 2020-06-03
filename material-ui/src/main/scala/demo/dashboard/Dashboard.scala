package demo.dashboard

import org.scalablytyped.runtime.StringDictionary
import slinky.core.annotations.react
import slinky.core.facade.Hooks
import slinky.core.{FunctionalComponent, ReactComponentClass}
import slinky.web.html._
import typings.classnames.{mod => classNames}
import typings.csstype.csstypeStrings.{hidden => _, _}
import typings.csstype.mod.OverflowXProperty
import typings.materialUiCore.anon.{PartialClassNameMapDrawer, Partialdurationnumberstri}
import typings.materialUiCore.createBreakpointsMod.Breakpoint
import typings.materialUiCore.createMuiThemeMod.Theme
import typings.materialUiCore.materialUiCoreStrings.{absolute, permanent}
import typings.materialUiCore.mod.PropTypes.Color
import typings.materialUiCore.typographyTypographyMod.{Style, TypographyProps}
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
        "toolbarIcon" -> (CSSProperties()
          .setDisplay(flex)
          .setAlignItems(center)
          .setJustifyContent(`flex-end`)
          .setPadding("0 8px")
          .combineWith(theme.mixins.toolbar): CSSProperties)
        ,
        "appBar" -> CSSProperties()
          .set("zIndex", theme.zIndex.drawer + 1)
          .setTransition(
            theme.transitions.create(
              js.Array("width", "margin"),
              Partialdurationnumberstri()
                .setEasing(theme.transitions.easing.sharp)
                .setDuration(theme.transitions.duration.enteringScreen)
            )
          ),
        "appBarShift" -> CSSProperties()
          .setMarginLeft(drawerWidth)
          .set("width", s"calc(100% - ${drawerWidth}px)")
          .setTransition(
            theme.transitions.create(
              js.Array("width", "margin"),
              Partialdurationnumberstri()
                .setEasing(theme.transitions.easing.sharp)
                .setDuration(theme.transitions.duration.enteringScreen)
            )
          ),
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
          .set("whiteSpace", nowrap)
          .set("width", drawerWidth)
          .setTransition(
            theme.transitions.create(
              "width",
              Partialdurationnumberstri()
                .setEasing(theme.transitions.easing.sharp)
                .setDuration(theme.transitions.duration.enteringScreen)
            )
          ),
        "drawerPaperClose" -> CSSProperties()
          .setOverflowX(OverflowXProperty.hidden)
          .setTransition(
            theme.transitions.create(
              "width",
              Partialdurationnumberstri()
                .setEasing(theme.transitions.easing.sharp)
                .setDuration(theme.transitions.duration.enteringScreen)
            )
          )
          .set("width", theme.spacing.unit * 7)
          .set(theme.breakpoints.up(Breakpoint.sm), StringDictionary("width" -> theme.spacing.unit * 9)),
        "appBarSpacer" -> (CSSProperties().combineWith(theme.mixins.toolbar): CSSProperties),
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
          .setMarginBottom(theme.spacing.unit * 2)
      )

    makeStyles[StyleRulesCallback[Theme, js.Object, String]](styles, WithStylesOptions())
  }

  val component: FunctionalComponent[Unit] = FunctionalComponent[Unit] {
    case () =>
      val (isOpen, setIsOpen) = Hooks.useState(true)
      val classes = styles(js.undefined)

      div()(
        Mui.CssBaseline(),
        Mui.AppBar
          .position(absolute)
          .className(classNames.default(classes("appBar"), if (isOpen) classes("appBarShift") else false))(
            Mui.Toolbar
              .disableGutters(!isOpen)
              .className(classes("toolbar"))(
                Mui.IconButton
                  .color(Color.inherit)
                  .`aria-label`("Open drawer")
                  .className(classes("menuButton"))(
                    Menu()
                  ),
                Mui
                  .Typography(h1())
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
          .classes(
            PartialClassNameMapDrawer().setPaper(
              classNames.default(
                classes("drawerPaper"),
                if (!isOpen) classes("drawerPaperClose")
                else false
              )
            )
          )(
            div()(
              Mui.IconButton()(
                ChevronLeft()
              )
            ),
            Mui.Divider(),
            Mui.List(ListItems.mainListItems),
             Mui.Divider(),
            Mui.List(ListItems.secondaryListItems)
          ),
        div(className := classes("content"))(
          div(className := classes("appBarSpacer"))(),
          Mui
            .Typography()
            .variant(Style.h4)
            .gutterBottom(true)
            .component("h2".asInstanceOf[ReactComponentClass[TypographyProps]])("Products"),
          div(className := classes("tableContainer"))(
            SimpleTable(())
          )
        )
      )
  }

}

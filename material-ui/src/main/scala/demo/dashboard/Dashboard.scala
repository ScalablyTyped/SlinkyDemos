package demo.dashboard

import demo.button.{ButtonTest, SelectDemo, StyledButtonDemo, StyledButtonHooksDemo}
import org.scalablytyped.runtime.StringDictionary
import slinky.core.annotations.react
import slinky.core.facade.Hooks
import slinky.core.{FunctionalComponent, ReactComponentClass}
import slinky.web.html._
import typings.classnames.{mod => classNames}
import typings.csstype.csstypeStrings.{hidden => _, _}
import typings.csstype.mod.{Color, OverflowXProperty}
import typings.materialUiCore.anon.{PartialClassNameMapDrawer, Partialdurationnumberstri}
import typings.materialUiCore.createBreakpointsMod.Breakpoint
import typings.materialUiCore.createMuiThemeMod.Theme
import typings.materialUiCore.materialUiCoreStrings.{absolute, permanent}
import typings.materialUiCore.mod.PropTypes
import typings.materialUiCore.typographyTypographyMod.{Style, TypographyProps}
import typings.materialUiCore.{components => Mui}
import typings.materialUiIcons.components.{ChevronLeft, Menu, Notifications}
import typings.materialUiStyles.makeStylesMod.StylesHook
import typings.materialUiStyles.mod.makeStyles
import typings.materialUiStyles.withStylesMod.{CSSProperties, StyleRulesCallback, WithStylesOptions}
import typings.reactRouter.mod.RouteProps
import typings.reactRouterDom.components.{BrowserRouter, Route}
import typings.reactRouterDom.components.Switch

import scala.scalajs.js

// https://v3.material-ui.com/getting-started/page-layout-examples/dashboard/
// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/Dashboard.js
@react object Dashboard {

  val drawerWidth = 240

  lazy val styles: StylesHook[StyleRulesCallback[Theme, js.Object, String]] =
    StyleBuilder[Theme, js.Object]
      .add("root", CSSProperties().setDisplay(flex))
      .add("toolbar", CSSProperties().setPaddingRight(24))
      .add(
        "toolbarIcon",
        theme =>
          CSSProperties()
            .setDisplay(flex)
            .setAlignItems(center)
            .setJustifyContent(`flex-end`)
            .setPadding("0 8px")
            .combineWith(theme.mixins.toolbar)
      )
      .add(
        "appBar",
        theme =>
          CSSProperties()
            .set("zIndex", theme.zIndex.drawer + 1)
            .setTransition(
              theme.transitions.create(
                js.Array("width", "margin"),
                Partialdurationnumberstri()
                  .setEasing(theme.transitions.easing.sharp)
                  .setDuration(theme.transitions.duration.enteringScreen)
              )
            )
      )
      .add(
        "appBarShift",
        theme =>
          CSSProperties()
            .setMarginLeft(drawerWidth)
            .set("width", s"calc(100% - ${drawerWidth}px)")
            .setTransition(
              theme.transitions.create(
                js.Array("width", "margin"),
                Partialdurationnumberstri()
                  .setEasing(theme.transitions.easing.sharp)
                  .setDuration(theme.transitions.duration.enteringScreen)
              )
            )
      )
      .add("menuButton", CSSProperties().setMarginLeft(12).setMarginRight(36))
      .add("menuButtonHidden", CSSProperties().setDisplay(none).setMarginRight(36))
      .add("title", CSSProperties().setFlexGrow(1))
      .add(
        "drawerPaper",
        theme =>
          CSSProperties()
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
            )
      )
      .add(
        "drawerPaperClose",
        theme =>
          CSSProperties()
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
            .set(theme.breakpoints.up(Breakpoint.sm), StringDictionary("width" -> theme.spacing.unit * 9))
      )
      .add("appBarSpacer", theme => CSSProperties().combineWith(theme.mixins.toolbar))
      .add(
        "content",
        theme =>
          CSSProperties()
            .setFlexGrow(1)
            .setPadding(theme.spacing.unit * 3)
            .setHeight("100vh")
            .setOverflow(auto)
      )
      .add("charContainer", CSSProperties().setMarginLeft(-22))
      .add("tableContainer", CSSProperties().setHeight(320))
      .add("h5", theme => CSSProperties().setMarginBottom(theme.spacing.unit * 2))
      .make

  type Props = Unit

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] {
    _ =>
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
                  .color(PropTypes.Color.inherit)
                  .`aria-label`("Open drawer")
                  .className(classes("menuButton"))(
                    Menu()
                  ),
                Mui
                  .Typography(h1())
                  .variant(Style.h6)
                  .color(PropTypes.Color.inherit)
                  .noWrap(true)
                  .className(classes("title"))("Dashboard"),
                Mui.IconButton.color(PropTypes.Color.inherit)(
                  Mui.Badge
                    .badgeContent(4)
                    .color(PropTypes.Color.secondary)(
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
            div()(Mui.IconButton()(ChevronLeft())),
            Mui.Divider(),
            Mui.List(ListItems.mainListItems),
            Mui.Divider(),
            Mui.List(ListItems.secondaryListItems)
          ),
        div(className := classes("content"))(
          div(className := classes("appBarSpacer"))(),
          BrowserRouter(
              Route(RouteProps()
                .setExact(true)
                .setPath("/")
                .setRender(_ => div(Mui
                  .Typography()
                  .variant(Style.h4)
                  .gutterBottom(true)
                  .component("h2".asInstanceOf[ReactComponentClass[TypographyProps]])("Products"),
                  div(className := classes("tableContainer"))(
                    SimpleTable()
                  )))),
            Route(RouteProps()
                .setExact(true)
                .setPath("/button")
                .setRender(_ => div(Mui
                  .Typography()
                  .variant(Style.h4)
                  .gutterBottom(true)
                  .component("h2".asInstanceOf[ReactComponentClass[TypographyProps]])("Button"),
                  div(className := classes("tableContainer"))(
                      ButtonTest("dear user"),
                      SelectDemo(List("one", "two", "three")),
                      StyledButtonDemo(),
                      StyledButtonHooksDemo()
                  )))),
            Route(RouteProps()
                .setExact(true)
                .setPath("/album")
                .setRender(_ => div(Mui
                  .Typography()
                  .variant(Style.h4)
                  .gutterBottom(true)
                  .component("h2".asInstanceOf[ReactComponentClass[TypographyProps]])("Album"),
                  div(className := classes("tableContainer"))(
                    SimpleTable()
                  ))))

          ))

      )
  }

}

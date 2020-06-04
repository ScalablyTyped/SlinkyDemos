package demo.album

import org.scalablytyped.runtime.StringDictionary
import slinky.core.annotations.react
import slinky.core.facade.Fragment
import slinky.core.{FunctionalComponent, ReactComponentClass}
import slinky.web.html._
import typings.classnames.{mod => classNames}
import typings.csstype.csstypeStrings.{column, relative, hidden => _, _}
import typings.materialUiCore.createMuiThemeMod.Theme
import typings.materialUiCore.materialUiCoreNumbers._
import typings.materialUiCore.materialUiCoreStrings.{center, contained, small, static, textSecondary}
import typings.materialUiCore.mod.PropTypes.Color
import typings.materialUiCore.typographyTypographyMod.{Style, TypographyProps}
import typings.materialUiCore.{components => Mui}
import typings.materialUiIcons.components.Camera
import typings.materialUiStyles.makeStylesMod.StylesHook
import typings.materialUiStyles.mod.makeStyles
import typings.materialUiStyles.withStylesMod.{CSSProperties, StyleRulesCallback, WithStylesOptions}

import scala.scalajs.js

// https://v3.material-ui.com/getting-started/page-layout-examples/album/
// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/album/Album.js
@react object Album {


  lazy val styles: StylesHook[StyleRulesCallback[Theme, js.Object, String]] = {
    lazy val styles: StyleRulesCallback[Theme, js.Object, String] = theme =>
      StringDictionary(
        "appBar" -> CSSProperties()
          .setPosition(relative),
        "icon" -> CSSProperties()
          .setMarginLeft(theme.spacing.unit * 2),
        "heroUnit" -> CSSProperties()
          .setBackgroundColor(theme.palette.background.paper),
        "heroContent" -> CSSProperties()
          .setMaxWidth(600)
          .setMargin("0 auto")
          .setPadding(s"${theme.spacing.unit * 8}px 0 ${theme.spacing.unit * 6}px"),
        "heroButtons" -> CSSProperties()
          .setMarginTop(theme.spacing.unit * 4),
        "layout" -> CSSProperties()
          .set("width", auto)
          .setMarginLeft(theme.spacing.unit * 3)
          .setMarginRight(theme.spacing.unit * 3)
          .set(theme.breakpoints.up(1100 + theme.spacing.unit * 3 * 2),
            CSSProperties()
              .set("width", 1100)
              .setMarginLeft(auto.asInstanceOf[String])
              .setMarginRight(auto.asInstanceOf[String])),
        "cardGrid" -> CSSProperties()
          .setPadding(s"${theme.spacing.unit * 8}px 0"),
        "card" -> CSSProperties()
          .setHeight("100%")
          .setDisplay(flex)
          .setFlexDirection(column),
        "cardMedia" -> CSSProperties()
          .setPaddingTop("56.25%"), // 16:9
        "cardContent" -> CSSProperties()
          .setFlexGrow(1),
        "footer" -> CSSProperties()
          .setBackgroundColor(theme.palette.background.paper)
          .setPadding(theme.spacing.unit * 6),
      )

    makeStyles[StyleRulesCallback[Theme, js.Object, String]](styles, WithStylesOptions())
  }

  val cards = 1 to 12

  val component: FunctionalComponent[Unit] = FunctionalComponent[Unit] {
    case () =>
      val classes = styles(js.undefined)

      Fragment()(
        Mui.CssBaseline(),
        Mui.AppBar
          .position(static)
          .className(classes("appBar"))(
            Mui.Toolbar()(
              Camera()
                .className(classes("icon"))(),
              Mui
                .Typography()
                .variant(Style.h6)
                .color(Color.inherit)("Album layout")
            )
          ),
        div()( // main
          div(className := classes("heroUnit"))(
            div(className := classes("heroContent"))(
              Mui
                .Typography(h1())
                .variant(Style.h2)
                .align(center)
                .color(textSecondary).paragraph(true)(
                """Something short and leading about the collection below—its contents, the creator, etc.
                  |Make it short and sweet, but not too short so folks don&apos;t simply skip over it
                  |entirely.""".stripMargin
              ),
              div(className := classes("heroButtons"))(
                Mui.Grid().container(true)
                  .spacing(`16`)
                  .justify(center)(
                    Mui.Grid().item(true)(
                      Mui.Button
                        .variant(contained)
                        .color(Color.primary)("Secondary action")
                    )
                  )

              )
            )
          ),
          div(className := classNames.default(classes("appBar"), classes("cardGrid")))(
            Mui.Grid.container(true).spacing(`40`)(
              cards.map { card =>
                Mui.Grid.item(true)
                  .withKey(card.toString)
                  .sm(`6`)
                  .md(`4`)
                  .lg(`3`)(
                    Mui.Card().className(classes("card"))(
                      Mui.CardMedia().className(classes("cardMedia"))
                        .image("data:image/svg+xml;charset=UTF-8,%3Csvg%20width%3D%22288%22%20height%3D%22225%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%20288%20225%22%20preserveAspectRatio%3D%22none%22%3E%3Cdefs%3E%3Cstyle%20type%3D%22text%2Fcss%22%3E%23holder_164edaf95ee%20text%20%7B%20fill%3A%23eceeef%3Bfont-weight%3Abold%3Bfont-family%3AArial%2C%20Helvetica%2C%20Open%20Sans%2C%20sans-serif%2C%20monospace%3Bfont-size%3A14pt%20%7D%20%3C%2Fstyle%3E%3C%2Fdefs%3E%3Cg%20id%3D%22holder_164edaf95ee%22%3E%3Crect%20width%3D%22288%22%20height%3D%22225%22%20fill%3D%22%2355595c%22%3E%3C%2Frect%3E%3Cg%3E%3Ctext%20x%3D%2296.32500076293945%22%20y%3D%22118.8%22%3EThumbnail%3C%2Ftext%3E%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E") // eslint-disable-line max-len
                        .title("Image title"),
                      Mui.CardContent().className("cardContent")(
                        Mui.Typography().gutterBottom(true)
                          .variant(Style.h5)
                          .component("h2".asInstanceOf[ReactComponentClass[TypographyProps]])("Heading"),
                        Mui.Typography()(
                          "This is a media card. You can use this section to describe the content."
                        )
                      ),
                      Mui.CardActions()(
                        Mui.Button()
                          .size(small)
                          .color(Color.primary)("View"),
                        Mui.Button()
                          .size(small)
                          .color(Color.primary)("Edit"),
                      )
                    )
                  )

              }
            )

          )
        ),
        footer(className := classes("footer"))(
          Mui.Typography().variant(Style.h6).align(center).gutterBottom(true)(
            "Footer"
          ),
          Mui.Typography().variant(Style.subtitle1).align(center)
            .color(textSecondary)
            .component("p".asInstanceOf[ReactComponentClass[TypographyProps]])(
              "Something here to give the footer a purpose!"
            )

        )

      )
  }

}


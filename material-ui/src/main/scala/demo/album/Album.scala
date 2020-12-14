package demo.album

import demo.StyleBuilder
import org.scalablytyped.runtime.StringDictionary
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Fragment
import slinky.web.html._
import typings.classnames.mod.^.{default => classNames}
import typings.csstype.csstypeStrings.{auto, column, flex, relative}
import typings.materialUiCore.components._
import typings.materialUiCore.createMuiThemeMod.Theme
import typings.materialUiCore.materialUiCoreNumbers._
import typings.materialUiCore.mod.PropTypes.Color
import typings.materialUiCore.typographyTypographyMod.Style
import typings.materialUiCore.{materialUiCoreStrings => strings}
import typings.materialUiIcons.{components => Icons}
import typings.materialUiStyles.makeStylesMod.StylesHook
import typings.materialUiStyles.withStylesMod.{CSSProperties, Styles}

import scala.scalajs.js

// https://v3.material-ui.com/getting-started/page-layout-examples/album/
// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/album/Album.js
@react object Album {
  lazy val styles: StylesHook[Styles[Theme, js.Object, String]] =
    StyleBuilder[Theme, js.Object]
      .add("appBar", CSSProperties().setPosition(relative))
      .add("icon", theme => CSSProperties().setMarginRight(theme.spacing.unit * 2))
      .add("heroUnit", theme => CSSProperties().setBackgroundColor(theme.palette.background.paper))
      .add(
        "heroContent",
        theme =>
          CSSProperties()
            .setMaxWidth(600)
            .setMargin("0 auto")
            .setPadding(s"${theme.spacing.unit * 8}px 0 ${theme.spacing.unit * 6}px")
      )
      .add("heroButtons", theme => CSSProperties().setMarginTop(theme.spacing.unit * 4))
      .add(
        "layout",
        theme =>
          CSSProperties()
            .setWidth("auto")
            .setMarginLeft(theme.spacing.unit * 3)
            .setMarginRight(theme.spacing.unit * 3)
            .set(
              theme.breakpoints.up(1100 + theme.spacing.unit * 3 * 2),
              CSSProperties()
                .setWidth(1100)
                .setMarginLeft(auto.asInstanceOf[String])
                .setMarginRight(auto.asInstanceOf[String])
            )
      )
      .add("cardGrid", theme => CSSProperties().setPadding(s"${theme.spacing.unit * 8}px 0"))
      .add("card", CSSProperties().setHeight("100%").setDisplay(flex).setFlexDirection(column))
      .add("cardMedia", CSSProperties().setPaddingTop("56.25%")) // 16:9
      .add("cardContent", CSSProperties().setFlexGrow(1))
      .add(
        "footer",
        theme => CSSProperties().setBackgroundColor(theme.palette.background.paper).setPadding(theme.spacing.unit * 6)
      )
      .hook

  val cards: Seq[Int] = 1 to 12

  type Props = Unit

  val component: FunctionalComponent[Unit] = FunctionalComponent[Unit] { _ =>
    val classes = styles(js.undefined)

    Fragment(
      CssBaseline(),
      AppBar
        .position(strings.static)
        .className(classes("appBar"))(
          Toolbar(
            Icons.PhotoCamera.className(classes("icon"))(),
            Typography.variant(Style.h6).color(Color.inherit)("Album layout")
          )
        ),
      main(
        div(className := classes("heroUnit"))(
          div(className := classes("heroContent"))(
            Typography
              .variant(Style.h2)
              .align(strings.center)
              .color(strings.textSecondary)
              .gutterBottom(true)
              .component("h1")(
                "Album Layout"
              ),
            Typography
              .variant(Style.h6)
              .align(strings.center)
              .color(strings.textSecondary)
              .paragraph(true)(
                """Something short and leading about the collection below—its
                  |contents, the creator, etc. Make it short and sweet, but not too
                  |short so folks don't simply skip over it entirely.""".stripMargin
              ),
            div(className := classes("heroButtons"))(
              Grid
                .container(true)
                .spacing(`16`)
                .justify(strings.center)(
                  Grid.item(true)(Button.variant(strings.contained).color(Color.primary)("Main call to action")),
                  Grid.item(true)(Button.variant(strings.outlined).color(Color.primary)("Secondary action"))
                )
            )
          )
        ),
        div(className := classNames(StringDictionary[js.Any](classes("layout") -> true, classes("cardGrid") -> true)))(
          Grid
            .container(true)
            .spacing(`40`)(cards.map { card =>
              Grid
                .item(true)
                .withKey(card.toString)
                .sm(`6`)
                .md(`4`)
                .lg(`3`)(
                  Card.className(classes("card"))(
                    CardMedia
                      .className(classes("cardMedia"))
                      .image(
                        "data:image/svg+xml;charset=UTF-8,%3Csvg%20width%3D%22288%22%20height%3D%22225%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%20288%20225%22%20preserveAspectRatio%3D%22none%22%3E%3Cdefs%3E%3Cstyle%20type%3D%22text%2Fcss%22%3E%23holder_164edaf95ee%20text%20%7B%20fill%3A%23eceeef%3Bfont-weight%3Abold%3Bfont-family%3AArial%2C%20Helvetica%2C%20Open%20Sans%2C%20sans-serif%2C%20monospace%3Bfont-size%3A14pt%20%7D%20%3C%2Fstyle%3E%3C%2Fdefs%3E%3Cg%20id%3D%22holder_164edaf95ee%22%3E%3Crect%20width%3D%22288%22%20height%3D%22225%22%20fill%3D%22%2355595c%22%3E%3C%2Frect%3E%3Cg%3E%3Ctext%20x%3D%2296.32500076293945%22%20y%3D%22118.8%22%3EThumbnail%3C%2Ftext%3E%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E"
                      ) /* eslint-disable-line max-len*/
                      .title("Image title"),
                    CardContent
                      .className("cardContent")(
                        Typography
                          .gutterBottom(true)
                          .variant(Style.h5)
                          .component("h2")("Heading"),
                        Typography(
                          "This is a media card. You can use this section to describe the content."
                        )
                      ),
                    CardActions(
                      Button.size(strings.small).color(Color.primary)("View"),
                      Button.size(strings.small).color(Color.primary)("Edit")
                    )
                  )
                )

            })
        )
      ),
      footer(className := classes("footer"))(
        Typography
          .variant(Style.h6)
          .align(strings.center)
          .gutterBottom(true)(
            "Footer"
          ),
        Typography
          .variant(Style.subtitle1)
          .align(strings.center)
          .color(strings.textSecondary)
          .component("p")(
            "Something here to give the footer a purpose!"
          )
      )
    )
  }
}

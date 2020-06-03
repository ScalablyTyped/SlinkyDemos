package demo.album

import demo.StyleBuilder
import org.scalablytyped.runtime.StringDictionary
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.{Fragment, ReactElement}
import slinky.web.html.{aria, role, span}
import typings.classnames.{mod => classNames}
import typings.csstype.csstypeStrings.absolute
import typings.materialUiCore.components._
import typings.materialUiCore.createMuiThemeMod.Theme
import typings.materialUiCore.materialUiCoreStrings.{center, textSecondary}
import typings.materialUiCore.mod.PropTypes.Color
import typings.materialUiStyles.makeStylesMod.StylesHook
import typings.materialUiStyles.withStylesMod._

import scala.scalajs.js

// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/modules/components/AppTheme.js
@react object AppTheme {

  lazy val styles: StylesHook[Styles[Theme, js.Object, String]] =
    StyleBuilder[Theme, js.Object]
      .add(
        "credit",
        theme => CSSProperties().setMarginTop(theme.spacing.unit * 6).setMarginBottom(theme.spacing.unit * 4)
      )
      .add("hideCredit", CSSProperties().setPosition(absolute).set("top", 0))
      .hook

  case class Props(children: ReactElement, description: String, hideCredit: Boolean = false, title: String)

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { props =>
    val classes = styles(js.undefined)
    Fragment(
      props.children,
      Typography
        .color(textSecondary)
        .align(center)
        .className(
          classNames(StringDictionary[js.Any](classes("credit") -> true, classes("hideCredit") -> props.hideCredit))
        )(
          "Built with ",
          span(role := "img", aria := "Love")("❤"),
          Link.color(Color.inherit).href("/")("ScalablyTyped Material-UI"),
          " team."
        )
    )
  }
}

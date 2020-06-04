package demo.album

import org.scalablytyped.runtime.StringDictionary
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.{Fragment, ReactElement}
import slinky.web.html.{aria, role, span}
import typings.classnames.{mod => classNames}
import typings.csstype.csstypeStrings.{hidden => _, _}
import typings.materialUiCore.createMuiThemeMod.Theme
import typings.materialUiCore.materialUiCoreStrings.{center, textSecondary}
import typings.materialUiCore.mod.PropTypes.Color
import typings.materialUiCore.{components => Mui}
import typings.materialUiStyles.makeStylesMod.StylesHook
import typings.materialUiStyles.mod.makeStyles
import typings.materialUiStyles.withStylesMod._

import scala.scalajs.js


// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/modules/components/AppTheme.js
@react object AppTheme {

  lazy val styles: StylesHook[StyleRulesCallback[Theme, js.Object, String]] = {
    lazy val styles: StyleRulesCallback[Theme, js.Object, String] = theme =>
      StringDictionary(
        "credit" -> CSSProperties()
          .setMarginTop(theme.spacing.unit * 6)
          .setMarginBottom(theme.spacing.unit * 4),
        "hideCredit" -> CSSProperties()
          .setPosition(absolute)
          .set("top", 0)
      )

    makeStyles[StyleRulesCallback[Theme, js.Object, String]](styles, WithStylesOptions())
  }

  case class Props(children: ReactElement,
                   classes: ClassNameMap[ClassKeyOfStyles[StyleRulesCallback[Theme, js.Object, String]]],
                   description: String,
                   hideCredit: Boolean = false,
                   title: String
                  )

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] {
    case props =>
      Fragment()(
        props.children,
        Mui.Typography()
          .color(textSecondary)
          .align(center)
          .className(classNames.default(props.classes("credit"), StringDictionary(props.classes("hideCredit") -> props.hideCredit)))(
          "Built with "
          span(role := "img", aria := "Love")("❤"️),
          Mui.Link()
            .color(Color.inherit)
            .href("/")(
              "ScalablyTyped Material-UI"
            )
          " team."
        )
      )
  }

}

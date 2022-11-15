package demo.customization

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.web.html.{div, style}
import typings.materialUiCore.components.Typography
import typings.materialUiCore.stylesCreateMuiThemeMod.Theme
import typings.react.mod.CSSProperties

// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/customization/themes/WithTheme.js
@react object WithTheme {

  case class Props(theme: Theme)

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { props =>
    val theme = props.theme
    val primaryText = theme.palette.text.primary;
    val primaryColor = theme.palette.primary.main;

    div(style := CSSProperties().setWidth(300))(
      Typography.style(
        CSSProperties()
          .setBackgroundColor(primaryColor)
          .setPadding(s"${theme.spacing.unit}px ${theme.spacing.unit * 2}px")
          .setColor(theme.palette.common.white)
      )(s"Primary color $primaryColor"),
      Typography.style(
        CSSProperties()
          .setBackgroundColor(theme.palette.background.default)
          .setPadding(s"${theme.spacing.unit}px ${theme.spacing.unit * 2}px")
          .setColor(primaryText)
      )(s"Primary color $primaryText")
    )
  }

}

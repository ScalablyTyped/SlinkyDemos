package demo.customization

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import typings.materialUiCore.colorsMod.purple
import typings.materialUiCore.components.{Button, MuiThemeProvider}
import typings.materialUiCore.stylesCreateMuiThemeMod.{Theme, ThemeOptions}
import typings.materialUiCore.stylesCreatePaletteMod.{PaletteColorOptions, PaletteOptions}
import typings.materialUiCore.stylesCreateTypographyMod.TypographyOptions
import typings.materialUiCore.mod.PropTypes.Color
import typings.materialUiCore.stylesMod

// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/customization/themes/Palette.js
@react object Palette {

  val theme: Theme = stylesMod
    .createMuiTheme(
      ThemeOptions()
        .setTypography(TypographyOptions().setUseNextVariants(true))
        .setPalette(
          PaletteOptions()
            .setPrimary(
              PaletteColorOptions.SimplePaletteColorOptions(purple.`500`)
            ) // Purple and green play nicely together.
            .setSecondary(PaletteColorOptions.SimplePaletteColorOptions("#11cb5f")) // This is just green.A700 as hex.
        )
    )

  type Props = Unit

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { _ =>
    MuiThemeProvider(theme)(
      Button.color(Color.primary)("Primary"),
      Button.color(Color.secondary)("Secondary")
    )
  }
}

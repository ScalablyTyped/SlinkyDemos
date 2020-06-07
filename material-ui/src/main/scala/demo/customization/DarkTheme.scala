package demo.customization

import typings.materialUiCore.createMuiThemeMod.{Theme, ThemeOptions}
import typings.materialUiCore.createPaletteMod.PaletteOptions
import typings.materialUiCore.createTypographyMod.TypographyOptions
import typings.materialUiCore.mod.PaletteType
import typings.materialUiCore.stylesMod
import typings.materialUiStyles.components.ThemeProvider


// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/customization/themes/DarkTheme.js
object DarkTheme {

  val theme: Theme = stylesMod
    .createMuiTheme(
      ThemeOptions()
        .setTypography(TypographyOptions().setUseNextVariants(true))
        .setPalette(PaletteOptions()
          .setType(PaletteType.dark) // Switching the dark mode on is a single property value change.
        ))

  def apply() =
    ThemeProvider(theme)(WithTheme(theme))

}



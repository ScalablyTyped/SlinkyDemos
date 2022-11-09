package demo.customization

import slinky.core.facade.ReactElement
import typings.materialUiCore.stylesCreateMuiThemeMod.{Theme, ThemeOptions}
import typings.materialUiCore.stylesCreatePaletteMod.PaletteOptions
import typings.materialUiCore.stylesCreateTypographyMod.TypographyOptions
import typings.materialUiCore.mod.PaletteType
import typings.materialUiCore.stylesMod.createMuiTheme
import typings.materialUiStyles.components.ThemeProvider

// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/customization/themes/DarkTheme.js
object DarkTheme {
  val theme: Theme = createMuiTheme(
    ThemeOptions()
      .setTypography(TypographyOptions().setUseNextVariants(true))
      .setPalette(
        PaletteOptions().setType(PaletteType.dark) /* Switching the dark mode on is a single property value change.*/
      )
  )

  def apply(): ReactElement =
    ThemeProvider(theme)(WithTheme(theme))
}

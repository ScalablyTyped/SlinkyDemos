package demo.login

import demo.StyleBuilder
import typings.csstype.csstypeStrings._
import typings.materialUiCore.stylesCreateMuiThemeMod.Theme
import typings.materialUiCore.stylesSpacingMod.Spacing
import typings.materialUiStyles.makeStylesMod.StylesHook
import typings.materialUiStyles.withStylesMod.{CSSProperties, Styles}

import scala.scalajs.js

// https://github.com/flatlogic/react-material-admin/blob/master/src/pages/login/styles.js
object Styles {

  lazy val styles: StylesHook[Styles[Theme, js.Object, String]] =
    StyleBuilder[Theme, js.Object]
      .add(
        "container",
        theme =>
          CSSProperties()
            .setHeight("100vh")
            .setWidth("100vw")
            .setDisplay(flex)
            .setJustifyContent(center)
            .setAlignItems(center)
            .setPosition(typings.csstype.csstypeStrings.absolute)
            .setTop(0)
            .setLeft(0)
      )
      .add(
        "logotypeContainer",
        theme =>
          CSSProperties()
            .setBackgroundColor(theme.palette.primary.main)
            .setWidth("60%")
            .setHeight("100%")
            .setDisplay(flex)
            .setFlexDirection(typings.csstype.csstypeStrings.column)
            .setJustifyContent(center)
            .setAlignItems(center)
            .set(
              theme.breakpoints.down(typings.materialUiCore.materialUiCoreStrings.md),
              CSSProperties()
                .setWidth("50%")
            )
            .set(
              theme.breakpoints.down(typings.materialUiCore.materialUiCoreStrings.md),
              CSSProperties()
                .setDisplay(none)
            )
      )
      .add(
        "logotypeImage",
        theme =>
          CSSProperties()
            .setWidth(165)
            .setMarginBottom(theme.setSpacing(Spacing(4)).spacing.unit)
      )
      .add(
        "logotypeText",
        theme =>
          CSSProperties()
            .setColor("white")
            .setFontWeight(500)
            .setFontSize(84)
            .set(
              theme.breakpoints.down(typings.materialUiCore.materialUiCoreStrings.md),
              CSSProperties()
                .setFontSize(48)
            )
      )
      .add(
        "formContainer",
        theme =>
          CSSProperties()
            .setWidth("40%")
            .setHeight("100%")
            .setDisplay(flex)
            .setFlexDirection(column)
            .setJustifyContent(center)
            .setAlignItems(center)
            .set(
              theme.breakpoints.down(typings.materialUiCore.materialUiCoreStrings.md),
              CSSProperties()
                .setWidth("50%")
            )
      )
      .add(
        "form",
        theme =>
          CSSProperties()
            .setWidth(320)
      )
      .add(
        "tab",
        theme =>
          CSSProperties()
            .setFontWeight(400)
            .setFontSize(18)
      )
      .add(
        "greeting",
        theme =>
          CSSProperties()
            .setFontWeight(500)
            .setTextAlign(center)
            .setMarginTop(theme.setSpacing(Spacing(4)).spacing.unit)
      )
      .add(
        "subGreeting",
        theme =>
          CSSProperties()
            .setFontWeight(500)
            .setTextAlign(center)
            .setMarginTop(theme.setSpacing(Spacing(2)).spacing.unit)
      )
      .add(
        "googleButton",
        theme =>
          CSSProperties()
            .setMarginTop(theme.setSpacing(Spacing(6)).spacing.unit)
            //.setBoxShadow( theme.customShadows.widget)
            .setBackgroundColor("white")
            .setWidth("100%")
            .setTextTransform(none)
      )
      .add(
        "googleButtonCreating",
        theme =>
          CSSProperties()
            .setMarginTop(0)
      )
      .add(
        "googleIcon",
        theme =>
          CSSProperties()
            .setWidth(30)
            .setMarginRight(theme.setSpacing(Spacing(2)).spacing.unit)
      )
      .add(
        "creatingButtonContainer",
        theme =>
          CSSProperties()
            .setMarginTop(theme.setSpacing(Spacing(2.5)).spacing.unit)
            .setHeight(46)
            .setDisplay(flex)
            .setJustifyContent(center)
            .setAlignItems(center)
      )
      .add(
        "createAccountButton",
        theme =>
          CSSProperties()
            .setHeight(46)
            .setTextTransform(none)
      )
      .add(
        "formDividerContainer",
        theme =>
          CSSProperties()
            .setMarginTop(theme.setSpacing(Spacing(4)).spacing.unit)
            .setMarginBottom(theme.setSpacing(Spacing(4)).spacing.unit)
            .setDisplay(flex)
            .setAlignItems(center)
      )
      .add(
        "formDividerWord",
        theme =>
          CSSProperties()
            .setPaddingLeft(theme.setSpacing(Spacing(2)).spacing.unit)
            .setPaddingRight(theme.setSpacing(Spacing(2)).spacing.unit)
      )
      .add(
        "formDivider",
        theme =>
          CSSProperties()
            .setFlexGrow(1)
            .setHeight(1)
            .setBackgroundColor(theme.palette.text.hint)
      )
      .add(
        "errorMessage",
        theme =>
          CSSProperties()
            .setTextAlign(center)
      )
      .add(
        "textFieldUnderline",
        theme =>
          CSSProperties()
            .set(
              "&:before",
              CSSProperties()
                .setBorderBottomColor(theme.palette.primary.light)
            )
            .set(
              "&:after",
              CSSProperties()
                .setBorderBottomColor(theme.palette.primary.main)
            )
            .set(
              "&:hover:before",
              CSSProperties()
                .setBorderBottomColor(s"${theme.palette.primary.light} !important")
            )
      )
      .add(
        "textField",
        theme =>
          CSSProperties()
            .setBorderBottomColor(theme.palette.background.default)
      )
      .add(
        "formButtons",
        theme =>
          CSSProperties()
            .setWidth("100%")
            .setMarginTop(theme.setSpacing(Spacing(4)).spacing.unit)
            .setDisplay(flex)
            .setJustifyContent("space-between")
            .setAlignItems(center)
      )
      .add(
        "forgetButton",
        theme =>
          CSSProperties()
            .setTextTransform(none)
            .setFontWeight(400)
      )
      .add(
        "loginLoader",
        theme =>
          CSSProperties()
            .setMarginLeft(theme.setSpacing(Spacing(4)).spacing.unit)
      )
      .add(
        "copyright",
        theme =>
          CSSProperties()
            .setMarginTop(theme.setSpacing(Spacing(4)).spacing.unit)
            .setWhiteSpace(nowrap)
            .set(
              theme.breakpoints.up(typings.materialUiCore.materialUiCoreStrings.md),
              CSSProperties()
                .setPosition(absolute)
                .setBottom(theme.setSpacing(Spacing(2)).spacing.unit)
            )
      )
      .hook

}

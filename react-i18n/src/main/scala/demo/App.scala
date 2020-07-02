package demo

import slinky.core.FunctionalComponent
import slinky.web.html._
import slinky.core.annotations.react
import typings.react.mod.CSSProperties
import typings.reactI18next.mod.useTranslation
import typings.reactI18next.components.Trans

import scala.scalajs.js

@react
object App {
  type Props = Unit

  val component = FunctionalComponent[Props] {_ =>
    val js.Tuple3(t, i18n, _) = useTranslation()
    val index = 11
    div(className := "App")(
      div(className := "App-header")(
        h2(t("Welcome to React")),
        button(onClick := {() => i18n.changeLanguage(I18n.de) })("de"),
        button(onClick := {() => i18n.changeLanguage(I18n.en) })("en"),
      ),
      div(className := "App-intro")(
        Trans()("To get started, edit ", code("src/App.js")," and save to reload."),
        Trans.i18nKey("welcome")("trans"),
        Trans()(index + 1, a("xxx")),
      ),
      div(style := CSSProperties().setMarginTop(40))(
        "Learn more ",
        a(href := "https://react.i18next.com")("https://react.i18next.com")
      )
    )
  }
}

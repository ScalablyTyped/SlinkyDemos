package demo

import slinky.core.FunctionalComponent
import slinky.web.html._
import slinky.core.annotations.react
import typings.i18next.mod.TOptionsBase
import typings.react.mod.CSSProperties
import typings.reactI18next.mod.useTranslation
import typings.reactI18next.components.Trans

import scala.scalajs.js

@react
object App {
  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>
    val js.Tuple3(t, i18n, _) = useTranslation()
    div(className := "App")(
      div(className := "App-header")(
        h2(t("Welcome to React")),
        button(onClick := { () => i18n.changeLanguage(I18n.de) })("de"),
        button(onClick := { () => i18n.changeLanguage(I18n.en) })("en")
      ),
      div(className := "App-intro")(
        div(Trans()("To get started, edit ", code("src/App.js"), " and save to reload.")),
        div(Trans.i18nKey("welcome")("trans")),
        div(t("dog", TOptionsBase().setCount(1))),
        div(t("dog", TOptionsBase().setCount(2))),
        div(t("friend", TOptionsBase().setContext("male"))),
        div(t("friend", TOptionsBase().setContext("female"))),
        div(t("The author is", TOptionsBase().set("author", "John"))),
     ),
      div(style := CSSProperties().setMarginTop(40))(
        "Learn more ",
        a(href := "https://react.i18next.com")("https://react.i18next.com")
      )
    )
  }
}

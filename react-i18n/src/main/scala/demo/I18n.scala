package demo

import org.scalablytyped.runtime.StringDictionary
import typings.i18next.i18nextBooleans.`false`
import typings.i18next.mod.{InitOptions, InterpolationOptions, default => i18n}
import typings.i18nextBrowserLanguagedetector.mod.{default => LanguageDetector}
import typings.reactI18next.mod.initReactI18next

import scala.scalajs.js

object I18n {

  val namespace = "translations"

  val en = "en"
  private val enTexts = StringDictionary[js.Any](
    "To get started, edit <1>src/App.js</1> and save to reload." -> "To get started, edit <1>src/App.js</1> and save to reload.",
    "Welcome to React" -> "Welcome to React and react-i18next",
    "welcome" -> "Hello <br/> <strong>World</strong>",
    "dog" -> "dog",
    "dog_plural" -> "dogs",
    "The author is" -> "The author is {{author}}",
    "friend" -> "A friend",
    "friend_male" -> "A boyfriend",
    "friend_female"-> "A girlfriend"
  )

  val de = "de"
  private val deTexts = StringDictionary[js.Any](
    "To get started, edit <1>src/App.js</1> and save to reload." -> "Starte in dem du, <1>src/App.js</1> editierst und speicherst.",
    "Welcome to React" -> "Willkommen bei React und react-i18next",
    "welcome" -> "Hallo <br/> <strong>Welt</strong>",
    "dog" -> "Hund",
    "dog_plural" -> "Hunde",
    "The author is" -> "Der Autor ist {{author}}",
    "friend" -> "Ein Freund",
    "friend_male" -> "Ein Freund",
    "friend_female" -> "Eine Freundin"
  )

  def initialize() =
    i18n
      .use(new LanguageDetector)
      .use(initReactI18next)
      .init(
        InitOptions()
          .setResources(
            StringDictionary(
              en -> StringDictionary(namespace -> enTexts),
              de -> StringDictionary(namespace -> deTexts)
            )
          )
          .setFallbackLng(en)
          .setDebug(true)
          .setDefaultNS(namespace)
          .setKeySeparator(`false`)
          .setInterpolation(InterpolationOptions().setEscapeValue(false))
      )
}

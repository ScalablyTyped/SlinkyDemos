package demo

import org.scalablytyped.runtime.StringDictionary
import typings.i18next.i18nextBooleans.`false`
import typings.i18next.mod.i18next.{InitOptions, InterpolationOptions, ResourceKey, ResourceLanguage}
import typings.i18next.mod.{default => i18n}
import typings.reactI18next.mod.initReactI18next

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object I18n {
  // there are no typescript definitions for this
  @js.native
  @JSImport("i18next-browser-languagedetector", JSImport.Default)
  object LanguageDetector extends js.Object

  val namespace = "translations"

  val en = "en"
  private val enTexts: ResourceKey = StringDictionary(
    "To get started, edit <1>src/App.js</1> and save to reload." -> ("To get started, edit <1>src/App.js</1> and save to reload."),
    "Welcome to React" -> "Welcome to React and react-i18next",
    "welcome" -> "Hello <br/> <strong>World</strong>"
  )

  val de = "de"
  private val deTexts: ResourceKey = StringDictionary(
    "To get started, edit <1>src/App.js</1> and save to reload." -> "Starte in dem du, <1>src/App.js</1> editierst und speicherst.",
    "Welcome to React" -> "Willkommen bei React und react-i18next",
    "welcome" -> "Hello <br/> <strong>World</strong>"
  )

  val default = i18n
    .use(LanguageDetector)
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

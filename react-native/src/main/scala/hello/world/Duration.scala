package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.native.{Text, View}
import typings.antDesignReactNative.components._
import typings.antDesignReactNative.{antDesignReactNativeStrings => antdStrings}
import typings.expoLocalization.mod.locale
import typings.moment.{mod => moment}
import typings.reactNative.mod.{TextStyle, ViewStyle}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("moment/locale/es", JSImport.Namespace)
@js.native
object es extends js.Object

@JSImport("moment/locale/fr", JSImport.Namespace)
@js.native
object fr extends js.Object

@JSImport("moment/locale/it", JSImport.Namespace)
@js.native
object it extends js.Object

@react object Duration {

  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>

    val locales = (es, fr, it)

    View(style = ViewStyle().setMargin(20))(
      Text(style = TextStyle().setFontSize(16))("Localized duration"),
      WhiteSpace().size(antdStrings.md),
      Text(s"Expo current locale: $locale"),
      WhiteSpace().size(antdStrings.md),
      Text(s"moment.js current locale: ${moment.apply().locale()}"),
      Text(s"moment.js duration en_US: ${moment.apply().locale("en_US").fromNow()}"),
      Text(s"moment.js duration es_ES: ${moment.apply().locale("es_ES").fromNow()}"),
      Text(s"moment.js duration fr_FR: ${moment.apply().locale("fr_FR").fromNow()}"),
      Text(s"moment.js duration it_IT: ${moment.apply().locale("it_IT").fromNow()}"),
    )
  }
}

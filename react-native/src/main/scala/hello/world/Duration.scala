package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks.useState
import slinky.native.{Text, View}
import typings.antDesignReactNative.components._
import typings.antDesignReactNative.{antDesignReactNativeStrings => antdStrings}
import typings.expoLocalization.mod.locale
import typings.moment.mod.{^ => moment}
import typings.dayjs.mod.{^ => dayjs}
import java.time.Instant
import scala.scalajs.js

@react object Duration {

  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>

    // should set day.js locale
   dayjs().locale("es")

    View(
      Text("Duration"),
      WhiteSpace().size(antdStrings.md),
      Text(s"Expo current locale: $locale"),
      WhiteSpace().size(antdStrings.md),
      Text(s"moment.js current locale: ${moment().locale()}"),
      Text(s"moment.js duration en_US: ${moment().locale("en_US").fromNow()}"),
      Text(s"moment.js duration es_ES: ${moment().locale("es_ES").fromNow()}"),
      Text(s"moment.js duration fr_FR: ${moment().locale("fr_FR").fromNow()}"),
      Text(s"moment.js duration it_IT: ${moment().locale("it_IT").fromNow()}"),
      WhiteSpace().size(antdStrings.md),
      Text(s"day.js current locale: ${dayjs().locale()}")
    )
  }
}

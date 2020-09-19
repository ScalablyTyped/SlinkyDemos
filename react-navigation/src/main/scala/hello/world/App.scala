package hello.world

import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.Hooks.useState
import slinky.core.facade.ReactElement
import slinky.native.{ScrollView, Text, View}

@react object App {

  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>

    View(Text("Whatever"))
  }
}

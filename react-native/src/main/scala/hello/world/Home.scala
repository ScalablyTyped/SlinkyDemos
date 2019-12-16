package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks.useState
import slinky.native.{ScrollView, Text, View}

import scala.scalajs.js.Dynamic.literal

@react object Home {

  case class Props(redirPath: String)

  val component = FunctionalComponent[Props] { props =>

    View(style = literal(
      margin = 20
    ))(
      Redir(props.redirPath, RoutePath.HOME.path),
      Text(style = literal(
        fontSize = 16
      ))("This is a demo written in Scala through Scala.js, Slinky, ScalablyTyped and Expo.\n\n"
        + "It uses components from Antd Native and React Router Native.")
    )
  }
}

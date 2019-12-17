package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.native.{Text, View}

import typings.reactDashRouter.reactDashRouterMod.`match`

import scala.scalajs.js.Dynamic.literal

@react object Home extends Redirectable {

  case class Props(redirPath: String, `match`: `match`[Unit])

  val component = FunctionalComponent[Props] { props =>

    checkRedirection(
      props.redirPath,
      props.`match`.path,
      View(
        style = literal(
          margin = 20
        )
      )(
        Text(
          style = literal(
            fontSize = 16
          )
        )(
          "This is a demo written in Scala through Scala.js, Slinky, ScalablyTyped and Expo.\n\n"
            + "It uses components from Antd Native and React Router Native."
        )
      )
    )
  }
}

package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import typingsSlinky.reactDashNative.components.{Text, View}
import typingsSlinky.reactDashNative.reactDashNativeMod.{TextStyle, ViewStyle}
import typingsSlinky.reactDashRouter.reactDashRouterMod.`match`

@react object Home extends Redirectable {

  case class Props(redirPath: String, `match`: `match`[_])

  val component = FunctionalComponent[Props] { props =>
    checkRedirection(
      props.redirPath,
      props.`match`.path,
      View(style = ViewStyle(margin = 20))(
        Text(style = TextStyle(fontSize = 16))(
          "This is a demo written in Scala through Scala.js, Slinky, ScalablyTyped and Expo.\n\n" +
            "It uses components from Antd Native and React Router Native."
        )
      )
    )
  }
}

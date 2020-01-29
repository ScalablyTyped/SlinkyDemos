package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.native.{Text, View}
import typings.reactNative.mod.{TextStyle, ViewStyle}
import typings.reactRouter.mod.`match`

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

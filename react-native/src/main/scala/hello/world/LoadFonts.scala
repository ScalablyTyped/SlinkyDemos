package hello.world

import org.scalablytyped.runtime.{StringDictionary, TopLevel}
import slinky.core.FunctionalComponent
import slinky.native.Text
import typings.expo.components.AppLoading
import typings.expoFont.fontTypesMod.FontSource
import typings.expoFont.{mod => Font}
import typings.react.mod.{useEffect, useState}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.util.{Failure, Success}

object LoadFonts {
  object Fonts {
    @JSImport("../../node_modules/@ant-design/icons-react-native/fonts/antoutline.ttf", JSImport.Namespace)
    @js.native
    object AntdIconOutline extends TopLevel[FontSource]

    @JSImport("../../node_modules/@ant-design/icons-react-native/fonts/antfill.ttf", JSImport.Namespace)
    @js.native
    object AntdIconFill extends TopLevel[FontSource]

    val All: StringDictionary[FontSource] = StringDictionary(
      "antoutline" -> AntdIconOutline,
      "antfill" -> AntdIconFill
    )
  }

  sealed trait State
  object State {
    case object Initial extends State
    case object Loading extends State
    case class Error(msg: String) extends State
    case object Success extends State
  }

  val component = FunctionalComponent[Unit] {
    case () =>
      val js.Tuple2(state, setState) = useState[State](State.Initial)

      useEffect { () =>
        if (state == State.Initial) {
          setState(State.Loading)
          Font
            .loadAsync(Fonts.All)
            .toFuture
            .onComplete {
              case Failure(exception) =>
                setState(State.Error(exception.toString))
              case Success(_) =>
                setState(State.Success)
            }
        }
      }

      state match {
        case State.Initial | State.Loading => AppLoading.AnonAutoHideSplash()
        case State.Error(msg)              => Text()(s"Could not load fonts: $msg")
        case State.Success                 => App()
      }
  }
}

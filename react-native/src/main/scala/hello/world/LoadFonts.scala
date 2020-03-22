package hello.world

import org.scalablytyped.runtime.TopLevel
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.native.Text
import typings.expo.components.AppLoading
import typings.expoFont.fontTypesMod.FontSource
import typings.expoFont.{mod => Font}
import typings.react.mod.{useEffect, useState}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.util.{Failure, Success}

@JSImport("../../node_modules/@ant-design/icons-react-native/fonts/antoutline.ttf", JSImport.Namespace)
@js.native
object AntdIconOutline extends TopLevel[FontSource]

@JSImport("../../node_modules/@ant-design/icons-react-native/fonts/antfill.ttf", JSImport.Namespace)
@js.native
object AntdIconFill extends TopLevel[FontSource]

@react
object LoadFonts {
  type Props = Unit
  case class State(result: Option[Either[String, Unit]])
  val initialState = State(None)

  val component = FunctionalComponent[Unit] {
    case () =>
      val js.Tuple2(state, setState) = useState[State](State(None))
      useEffect { () =>
        js.Promise
          .all(js.Array(Font.loadAsync("antoutline", AntdIconOutline), Font.loadAsync("antfill", AntdIconFill)))
          .toFuture
          .onComplete {
            case Failure(exception) => setState(State(Some(Left(exception.toString))))
            case Success(_)         => setState(State(Some(Right(()))))
          }
      }

      state.result match {
        case Some(Right(_))    => App()
        case Some(Left(value)) => Text()(s"Could not load fonts: $value")
        case None              => AppLoading.AnonAutoHideSplash()
      }
  }
}

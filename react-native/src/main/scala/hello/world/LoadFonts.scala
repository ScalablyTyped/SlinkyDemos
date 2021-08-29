package hello.world

import org.scalablytyped.runtime.{StringDictionary, TopLevel}
import slinky.core.facade.Hooks.{useEffect, useState}
import slinky.core.FunctionalComponent
import slinky.native.Text
import typings.expoAppLoading.components.AppLoading
import typings.expoFont.fontTypesMod.FontSource
import typings.expoFont.{mod => Font}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.Promise
import scala.scalajs.js.annotation.JSImport
import scala.util.{Failure, Success}

/* we must load these fonts manually to use antd design */
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
    case object Loading extends State
    case class Error(msg: String) extends State
    case object Success extends State
  }

  val component = FunctionalComponent[Unit] {
    case () =>
      val (state, setState) = useState(State.Loading: State)

      useEffect(
        () =>
          Font
            .loadAsync(Fonts.All)
            .toFuture
            .onComplete {
              case Failure(exception) => setState(State.Error(exception.getMessage))
              case Success(_)         => setState(State.Success)
            },
        Seq()
      )

      state match {
        case State.Loading    => AppLoading.AutoHideSplash(_ => (), () => (), () => Promise.resolve[Unit](()))
        case State.Error(msg) => Text()(s"Could not load fonts: $msg")
        case State.Success    => App()
      }
  }
}

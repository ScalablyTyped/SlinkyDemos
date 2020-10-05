package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks.useState
import slinky.native.View
import typings.antDesignReactNative.components._
import typings.antDesignReactNative.{antDesignReactNativeStrings => antdStrings}
import typings.expoAv.aVMod.{AVPlaybackStatus, AVPlaybackStatusToSet}
import typings.expoAv.mod.Audio.Sound
import typings.expoAv.anon.{AndroidImplementation, DidJustFinish, Headers}
import typings.expoAv.expoAvBooleans.`true`

import scala.scalajs.js

@react object PlayAudio {

  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>
    val uri: String = "https://upload.wikimedia.org/wikipedia/commons/b/b2/Funky_bassline_F7-Bb7.ogg"
    val (isPlayable, updateIsPlayable) = useState(true)

    val soundObject = new Sound

    def checkStatus(s: AVPlaybackStatus): Either[AndroidImplementation, DidJustFinish] =
      if (s.asInstanceOf[js.Dynamic].isLoaded.asInstanceOf[Boolean]) Right(s.asInstanceOf[DidJustFinish])
      else Left(s.asInstanceOf[AndroidImplementation])

    soundObject.setOnPlaybackStatusUpdate { status =>
      checkStatus(status) match {
        case Left(_) => ()
        case Right(value) =>
          if (value.didJustFinish) {
            soundObject.unloadAsync()
            updateIsPlayable(true)
          }
      }
    }

    def loadAndPlay() =
      soundObject.loadAsync(
        Headers(uri = uri),
        AVPlaybackStatusToSet().setShouldPlay(true) // optional, but you can set a bunch of stuff here
      )

    View(
      WhiteSpace().size(antdStrings.md),
      Flex(
        Flex(
          Button(Icon("sound"))
            .disabled(!isPlayable)
            .`type`(antdStrings.primary)
            .onPress { _ =>
              updateIsPlayable(false)
              loadAndPlay()
            },
          Text("Play")
        ).direction(antdStrings.column)
      ).justify(antdStrings.around)
    )
  }
}

package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks.useState
import slinky.native.View
import typings.antDesignReactNative.components._
import typings.antDesignReactNative.{antDesignReactNativeStrings => antdStrings}
import typings.expoAv.aVMod.AVPlaybackStatusToSet
import typings.expoAv.mod.Audio.Sound
import typings.expoAv.anon.{DidJustFinish, Headers}
import typings.expoAv.expoAvBooleans.`true`

@react object PlayAudio {

  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>
    val uri: String = "https://upload.wikimedia.org/wikipedia/commons/b/b2/Funky_bassline_F7-Bb7.ogg"
    val (isPlayable, updateIsPlayable) = useState(true)

    val soundObject = new Sound

    soundObject.setOnPlaybackStatusUpdate { status =>
      Option(status.asInstanceOf[DidJustFinish]) match {
        case Some(obj) =>
          if (obj.isLoaded == `true`)
            if (obj.didJustFinish) {
              soundObject.unloadAsync()
              updateIsPlayable(true)
            }
        case None => ()
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
            .onPress(_ => {
              updateIsPlayable(false)
              loadAndPlay()
            }),
          Text("Play")
        ).direction(antdStrings.column)
      ).justify(antdStrings.around),
    )
  }
}

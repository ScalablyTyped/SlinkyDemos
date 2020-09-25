package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks.useEffect
import slinky.native.View
import typings.antDesignReactNative.components._
import typings.antDesignReactNative.{antDesignReactNativeStrings => antdStrings}
import typings.expoAv.aVMod.AVPlaybackStatusToSet
import typings.expoAv.mod.Audio.Sound
import typings.expoAv.anon.Headers

import scala.concurrent.ExecutionContext.Implicits.global

@react object PlayAudio {

  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>
    val uri: String = "https://upload.wikimedia.org/wikipedia/commons/b/b2/Funky_bassline_F7-Bb7.ogg"
    val soundObject = new Sound

    useEffect(
      () => {
        soundObject
          .loadAsync(
            Headers(uri = uri),
            AVPlaybackStatusToSet().setShouldPlay(false) // optional, but you can set a bunch of stuff here
          )
        () => soundObject.unloadAsync()
      },
      Seq()
    )

    View(
      WhiteSpace().size(antdStrings.md),
      Flex(
        Flex(
          Button(Icon(name = "play-circle"))
            .`type`(antdStrings.primary)
            .onPress(_ => soundObject.playAsync()),
          Text("Play")
        ).direction(antdStrings.column),
        Flex(
          Button(Icon(name = "pause-circle"))
            .`type`(antdStrings.primary)
            .onPress(_ => soundObject.pauseAsync()),
          Text("Pause")
        ).direction(antdStrings.column),
        Flex(
          Button(Icon(name = "stop"))
            .`type`(antdStrings.primary)
            .onPress(_ => soundObject.stopAsync()),
          Text("Stop")
        ).direction(antdStrings.column)
      ).justify(antdStrings.around)
    )
  }
}

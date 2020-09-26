package hello.world

import org.scalajs.dom.console
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks.{useEffect, useState}
import slinky.native.View
import typings.antDesignReactNative.components._
import typings.antDesignReactNative.{antDesignReactNativeStrings => antdStrings}
import typings.expoAv.aVMod.{AVPlaybackStatus, AVPlaybackStatusToSet}
import typings.expoAv.mod.Audio.Sound
import typings.expoAv.anon.{AndroidImplementation, DidJustFinish, Headers}

import scala.concurrent.ExecutionContext.Implicits.global

@react object PlayAudio {

  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>
    val uri: String = "https://upload.wikimedia.org/wikipedia/commons/b/b2/Funky_bassline_F7-Bb7.ogg"
    val (status, updateStatus) = useState(None: Option[AVPlaybackStatus])
    val (isPlayButtonDisabled, updateIsPlayButtonDisabled) = useState(false)
    val (isPauseButtonDisabled, updateIsPauseButtonDisabled) = useState(true)

    val soundObject = new Sound

    useEffect(
      () => {
        soundObject
          .loadAsync(
            Headers(uri = uri),
            AVPlaybackStatusToSet().setShouldPlay(false) // optional, but you can set a bunch of stuff here
          )
        soundObject.setOnPlaybackStatusUpdate {
          status =>

            Option(status.asInstanceOf[DidJustFinish]) match {
              case Some(obj) =>
                //@todo this block causes [[Unhandled promise rejection: org.scalajs.linker.runtime.UndefinedBehaviorError: java.lang.ClassCastException: undefined is not an instance of java.lang.Boolean]]
                if (obj.isPlaying && !isPlayButtonDisabled) {
                  updateIsPlayButtonDisabled(true)
                  updateIsPauseButtonDisabled(false)
                } else if (!obj.isPlaying && isPlayButtonDisabled) {
                  updateIsPlayButtonDisabled(false)
                  updateIsPauseButtonDisabled(true)
                }

              case None => ()
            }
            //@todo this line causes [Unhandled promise rejection: Error: Cannot complete operation because sound is not loaded.]
            updateStatus(Some(status))
        }
        () => soundObject.unloadAsync()
      },
      Seq()
    )

    View(
      WhiteSpace().size(antdStrings.md),
      Flex(
        Flex(
          Button(Icon(name = "play-circle"))
            .disabled(isPlayButtonDisabled)
            .`type`(antdStrings.primary)
            .onPress(_ => soundObject.playAsync()),
          Text("Play")
        ).direction(antdStrings.column),
        Flex(
          Button(Icon(name = "pause-circle"))
            .disabled(isPauseButtonDisabled)
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

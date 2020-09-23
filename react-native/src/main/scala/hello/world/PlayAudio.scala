package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.native.{Text, View}
import typings.expoAv.aVMod.{AVPlaybackStatus, AVPlaybackStatusToSet}
import typings.expoAv.mod.Audio.Sound
import typings.expoAv.anon.Headers

import scala.concurrent.ExecutionContext.Implicits.global

@react object PlayAudio {

  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>
    val uri: String = "https://upload.wikimedia.org/wikipedia/commons/e/e4/En-us-tough.ogg"

    val soundObject = new Sound
    soundObject
      .loadAsync(
        Headers(uri = uri),
        AVPlaybackStatusToSet().setShouldPlay(true) // optional, but you can set a bunch of stuff here
      )
      .toFuture
      .flatMap((_: AVPlaybackStatus) => soundObject.playAsync().toFuture)

    View(Text("tough"))
  }
}

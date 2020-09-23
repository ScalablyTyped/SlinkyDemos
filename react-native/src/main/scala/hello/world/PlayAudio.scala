package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.native.{Text, View}
import typings.expoAv.aVMod.{AVPlaybackNativeSource, AVPlaybackSource}
import typings.expoAv.mod.Audio.Sound

import scala.scalajs.js
import scala.scalajs.js.Promise

@react object PlayAudio {

  type Props = Unit

  val component = FunctionalComponent[Props] {
    case () =>

      val uri: String = "https://upload.wikimedia.org/wikipedia/commons/e/e4/En-us-tough.ogg"
      val nativeSource: AVPlaybackNativeSource = AVPlaybackNativeSource(uri)
      val dictSource = js.Dictionary("uri" -> uri)
      val source: AVPlaybackSource = ???

      val p: Promise[typings.expoAv.anon.Sound] = Sound.createAsync(source)

      View(Text("messy"))

  }
}

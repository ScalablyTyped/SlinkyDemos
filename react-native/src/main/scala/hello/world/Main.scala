package hello.world

import slinky.core.ReactComponentClass
import slinky.hot

import scala.scalajs.{js, LinkingInfo}
import scala.scalajs.js.annotation.JSExportTopLevel

object Main {
  if (LinkingInfo.developmentMode) {
    hot.initialize()
  }

  @JSExportTopLevel("app")
  val app: ReactComponentClass[_] = LoadFonts.component
}

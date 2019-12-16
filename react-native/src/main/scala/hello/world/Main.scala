package hello.world

import slinky.core.ReactComponentClass
import slinky.hot
import slinky.native.AppRegistry

import scala.scalajs.js
import scala.scalajs.LinkingInfo
import scala.scalajs.js.annotation.JSExportTopLevel

object Main {
  if (LinkingInfo.developmentMode) {
    hot.initialize()
  }

  @JSExportTopLevel("app")
  val app: ReactComponentClass[_] = App.component
}

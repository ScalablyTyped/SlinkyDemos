package hello.world

import scala.scalajs.js
import scala.scalajs.LinkingInfo
import scala.scalajs.js.annotation.{JSExportTopLevel, JSImport}

import slinky.native.AppRegistry
import slinky.hot

object Main {
  if (LinkingInfo.developmentMode) {
    hot.initialize()
  }
  
  @JSExportTopLevel("app")
  val app = App.componentConstructor
}

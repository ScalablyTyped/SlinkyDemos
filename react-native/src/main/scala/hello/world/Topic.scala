package hello.world

import slinky.core._
import slinky.core.annotations.react
import slinky.native.Text
import typings.reactRouter.mod._

import scala.scalajs.js

@react object Topic {

  trait Param extends js.Object {
    val topicId: String
  }

  case class Props(`match`: `match`[Topic.Param])

  val component = FunctionalComponent[Props] { props =>
    Text(style = Styles.topicStyle)("Topic: " + props.`match`.params.topicId)
  }
}

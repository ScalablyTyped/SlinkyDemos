package hello.world

import slinky.core._
import slinky.core.annotations.react
import slinky.native.Text

import typings.reactRouter.mod._

import scala.scalajs.js

@react object Topic {

  @js.native
  trait Param extends js.Object {
    val topicId: String = js.native
  }

  case class Props(`match`: `match`[Topic.Param])

  val component = FunctionalComponent[Props] { props =>
    Text(style = Styles.topicStyle)("Topic: " + props.`match`.params.topicId)
  }

  val component2 = FunctionalComponent[Unit] { _ =>
    Text(style = Styles.topicStyle)("Topic: ")
  }
}

package demo

import org.scalajs.dom.window.alert
import slinky.core.AttrPair
import slinky.web.html._
import typings.storybookReact.mod.storiesOf
import typings.node.global.module

object Demo {
  def main(args: Array[String]): Unit =
    storiesOf("Button", module)
      .add("with text", ctx => button("Hello Button"))
      .add(
        "with some emoji",
        ctx =>
          button(
            onClick := (e => alert(s"x: ${e.pageX}, y: ${e.pageY}")),
            aria - "label" := "so cool",
            new AttrPair("role", "img")
          )(span("😀😎"))
      )
}

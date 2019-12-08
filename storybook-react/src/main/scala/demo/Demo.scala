package demo

import slinky.core.AttrPair
import slinky.web.html._
import typingsSlinky.atStorybookReact.atStorybookReactMod.storiesOf
import typingsSlinky.node.module
import typingsSlinky.std.window

object Demo {

  def main(args: Array[String]): Unit =
    storiesOf("Button", module)
      .add("with text", ctx => button("Hello Button"))
      .add(
        "with some emoji",
        ctx =>
          button(
            onClick := (e => window.alert(s"x: ${e.pageX}, y: ${e.pageY}")),
            aria - "label" := "so cool",
            new AttrPair("role", "img")
          )(span("😀😎"))
      )
}

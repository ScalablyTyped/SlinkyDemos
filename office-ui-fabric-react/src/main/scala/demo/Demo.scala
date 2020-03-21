package demo

import org.scalajs.dom
import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.Hooks
import slinky.web.ReactDOM
import slinky.web.html._
import typings.officeUiFabricReact.{components => Fabric}

object Demo {
  def main(argv: Array[String]): Unit =
    ReactDOM.render(App(name = "Dear user"), dom.document.getElementById("container"))
}

@react
object App {
  case class Props(name: String)

  val component = FunctionalComponent[Props] { props =>
    /* use a hook to keep state */
    val (state, setState) = Hooks.useState(1)

    val incrementButton = Fabric.Button(onClick := (() => setState(state + 1)))(
      s"Increment it, ${props.name}"
    )
    val text = Fabric.TextField(value := state.toString, disabled := true)
    div(text, incrementButton)
  }
}

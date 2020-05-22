package demo

import org.scalajs.dom
import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.Hooks
import slinky.web.ReactDOM
import slinky.web.html._
import typings.materialUiCore.{components => Mui}

object Demo {

  def main(argv: Array[String]): Unit =
    ReactDOM.render(
      div(
        ButtonTest("dear user"),
        SelectDemo(List("one", "two", "three"))
      ),
      dom.document.getElementById("container")
    )
}

@react
object ButtonTest {
  case class Props(name: String)

  val component = FunctionalComponent[Props] { props =>
    /* use a hook to keep state */
    val (state, setState) = Hooks.useState(1)

    val incrementButton = Mui.Button.onClick(_ => setState(state + 1))(
      s"Increment it, ${props.name}"
    )

    div(
      /* text field controlled by the value of the state hook above*/
      Mui.TextField.StandardTextFieldProps().value(state).disabled(true),
      incrementButton
    )
  }
}

@react
object SelectDemo {
  case class Props(values: List[String])

  val component = FunctionalComponent[Props] {
    case Props(values) =>
      val (chosen, setChosen) = Hooks.useState[String](values.head)

      val items = values.zipWithIndex.map {
        case (value, idx) => Mui.MenuItem.value(value).withKey(idx.toString)(value)
      }
      div(
        Mui.Select
          .value(chosen)
          .onChange((e, _) => setChosen(e.target_ChangeEvent.value))(items),
        Mui.TextField.StandardTextFieldProps()
          .value(chosen)
          .disabled(true)
      )
  }
}

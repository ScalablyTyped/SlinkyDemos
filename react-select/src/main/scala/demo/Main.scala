package demo

import org.scalajs.dom.document
import slinky.core.FunctionalComponent
import slinky.core.facade.Hooks
import slinky.web.ReactDOM
import slinky.web.html._
import typings.reactSelect.components.ReactSelect
import typings.reactSelect.typesMod.ValueType

import scala.scalajs.js

object Main {
  /* `ValueType` is a typical Javascript pattern for a context where you can have `null`, `undefined`, a value, or an array of values. */
  def toArray[T](x: ValueType[T]): js.Array[T] =
    if ((x eq null) || js.isUndefined(x)) js.Array()
    else if (js.Array.isArray(x)) x.asInstanceOf[js.Array[T]]
    else js.Array(x.asInstanceOf[T])

  // and `null` seems like a better empty value than `undefined` - go figure
  private val Empty: ValueType[MyOption] = null

  case class MyOption(myname: String, myvalue: Int)

  case class Props(options: js.Array[MyOption])

  val One: FunctionalComponent[Props] =
    FunctionalComponent[Props] {
      case Props(allOptions) =>
        val (chosen, setChosen) = Hooks.useState(Empty)

        div(
          ReactSelect[MyOption, js.Any]()
            .options(allOptions)
            // control value, react select can also control for you
            .value(chosen)
            .onChange((newChosen, _) => setChosen(newChosen))
            // or `MyOption could just have `label` and `value` members and extend `js.Object` instead of these last two
            .getOptionLabel(_.myname)
            .getOptionValue(_.myvalue.toString),
          toArray(chosen).headOption.fold("No selection")(opt => s"Selection: ${opt.myvalue}")
        )
    }

  val Multi: FunctionalComponent[Props] =
    FunctionalComponent[Props] {
      case Props(options) =>
        val (chosen, setChosen) = Hooks.useState(Empty)

        div(
          ReactSelect[MyOption, js.Any]()
            .options(options)
            .value(chosen)
            .onChange((newChosen, _) => setChosen(newChosen))
            .getOptionLabel(_.myname)
            .getOptionValue(_.myvalue.toString)
            .isMulti(true),
          s"Selection: ${toArray(chosen).map(_.myvalue).mkString(", ")}"
        )
    }

  def main(argv: Array[String]): Unit = {
    val options = js.Array(MyOption("value 1", 1), MyOption("value 2", 2), MyOption("another value", 42))
    ReactDOM.render(
      div(
        h2("Select one:"),
        One(Props(options)),
        h2("Select many:"),
        Multi(Props(options))
      ),
      document.body
    )
  }
}

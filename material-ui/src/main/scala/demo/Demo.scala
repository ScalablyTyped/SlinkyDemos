package demo

import org.scalablytyped.runtime.StringDictionary
import org.scalajs.dom
import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.Hooks
import slinky.web.ReactDOM
import slinky.web.html._
import typings.csstype.mod.NamedColor
import typings.materialUiCore.{stylesMod, components => Mui}
import typings.std.global.window

import scala.scalajs.js

object Demo {

  def main(argv: Array[String]): Unit =
    ReactDOM.render(
      div(
        ButtonTest("dear user"),
        SelectDemo(List("one", "two", "three")),
        StyledButtonDemo(()),
        StyledButtonHooksDemo(())
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
        Mui.TextField
          .StandardTextFieldProps()
          .value(chosen)
          .disabled(true)
      )
  }
}

@react
object StyledButtonDemo {
  val component = FunctionalComponent[Unit] {
    case () =>
      val usingWithStyles = {
        import typings.materialUiCore.withStylesMod.{CSSProperties, WithStylesOptions}

        val styleInjector =
          stylesMod.withStyles(
            StringDictionary("root" -> CSSProperties().setBackgroundColor(NamedColor.blue)),
            WithStylesOptions[String]()
          )

        Mui.Button
          .withComponent(c => styleInjector(c))
          .onClick(_ => window.alert("clicked"))("using withStyles")
      }

      val usingReactCss = {
        import typings.react.mod.CSSProperties
        Mui.Button
          .style(CSSProperties().setBackgroundColor(NamedColor.darkred))
          .onClick(_ => window.alert("clicked"))("direct css")
      }

      div(usingWithStyles, usingReactCss)
  }
}

// https://v3.material-ui.com/css-in-js/basics/
@react
object StyledButtonHooksDemo {

  import typings.materialUiStyles.makeStylesMod.StylesHook
  import typings.materialUiStyles.mod.makeStyles
  import typings.materialUiStyles.withStylesMod.{CSSProperties, StyleRules, Styles, WithStylesOptions}

  val useStyles: StylesHook[Styles[js.Object, js.Object, String]] = {
    val styles: StyleRules[js.Object, String] =
      StringDictionary(
        "root" -> CSSProperties()
          .setBackground("linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)")
          .setBorder(0)
          .setBorderRadius(3)
          .setBoxShadow("0 3px 5px 2px rgba(255, 105, 135, .3)")
          .setColor(NamedColor.white)
          .setHeight(48)
          .setPadding("0 30px")
      )

    makeStyles(styles, WithStylesOptions())
  }

  val component = FunctionalComponent[Unit] {
    case () =>
      val classes = useStyles(js.undefined)
      div(
        Mui.Button
          .className(classes("root"))
          .onClick(_ => window.alert("clicked"))("styles module with hook")
      )
  }
}

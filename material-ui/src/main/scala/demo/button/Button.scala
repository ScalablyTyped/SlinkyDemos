package demo.button

import org.scalablytyped.runtime.StringDictionary
import org.scalajs.dom
import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.Hooks
import slinky.web.ReactDOM
import slinky.web.html._
import typings.csstype.mod.{ColorProperty, NamedColor}
import typings.materialUiCore.createMuiThemeMod.{Theme, ThemeOptions}
import typings.materialUiCore.spacingMod.SpacingOptions
import typings.materialUiCore.{stylesMod, components => Mui}
import typings.materialUiStyles.components.ThemeProvider
import typings.std.global.window

import scala.scalajs.js

object Button {

  val theme: Theme = stylesMod.createMuiTheme(ThemeOptions().setSpacing(SpacingOptions().setUnit(2)))

  ReactDOM.render(
    // theme passed through react context and used in StyledButtonHooksDemo
    ThemeProvider(theme)(
      div(
        ButtonTest("dear user"),
        SelectDemo(List("one", "two", "three")),
        StyledButtonDemo(()),
        StyledButtonHooksDemo(())
      )
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
  import typings.materialUiStyles.withStylesMod.{CSSProperties, StyleRulesCallback, Styles, WithStylesOptions}

  class StyleProps(val favouriteColor: ColorProperty) extends js.Object

  val useStyles: StylesHook[Styles[Theme, StyleProps, String]] = {
    val root: js.Function1[StyleProps, CSSProperties] = props =>
      CSSProperties()
        .setBackground("linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)")
        .setBorder(0)
        .setBorderRadius(3)
        .setBoxShadow("0 3px 5px 2px rgba(255, 105, 135, .3)")
        .setColor(props.favouriteColor)
        .setHeight(48)
        .setPadding("0 30px")

    /* If you don't need direct access to theme, this could be `StyleRules[Props, String]` */
    val stylesCallback: StyleRulesCallback[Theme, StyleProps, String] = theme =>
      StringDictionary(
        "root" -> root,
        "outer" -> CSSProperties().setPadding(theme.spacing.unit * 3 + "px")
      )

    makeStyles(stylesCallback, WithStylesOptions())
  }

  val component = FunctionalComponent[Unit] {
    case () =>
      val classes = useStyles(new StyleProps(NamedColor.green))
      div(
        className := classes("outer"),
        Mui.Button
          .className(classes("root"))
          .onClick(_ => window.alert("clicked"))("styles module with hook")
      )
  }
}

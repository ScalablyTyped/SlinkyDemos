package demo

import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._
import typings.semanticUiReact.{
  components => Sui,
  semanticUiReactStrings => SuiStrings
}

import scala.language.implicitConversions

@react object App {
  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>
    div(
      Sui.Divider(horizontal = true)(
        Sui.Header(as = "h4")(
          Sui.Icon(name = SuiStrings.tag),
          "Button and Icon"
        )
      ),
      p(
        Sui.Button(primary = true)(
          "Primary"
        )
      ),
      p(
        Sui.Icon(name = SuiStrings.recycle)
      ),
      p(
        Sui.Button(
          Sui.Icon(name = SuiStrings.recycle)
        )
      ),
      p(
        Sui.Button(labelPosition = SuiStrings.left)(
          Sui.Icon(name = SuiStrings.pause),
          "Pause"
        )
      ),
      Sui.Divider(horizontal = true)(
        Sui.Header(as = "h4")(
          Sui.Icon(name = SuiStrings.tag),
          "Form and Checkbox"
        )
      ),
      Sui.Form(
        Sui.FormField()(
          label("First Name"),
          input(placeholder := "First Name")
        ),
        Sui.FormField()(
          label("Last Name"),
          input(placeholder := "Last Name")
        ),
        Sui.FormField()(
          Sui.Checkbox(label = "I agree to the Terms and Conditions".asInstanceOf[ReactElement])()
        ),
        Sui.FormField()(
          Sui.Checkbox(
            label = "I agree to the Cookie Policy".asInstanceOf[ReactElement],
            toggle = true
          )()
        ),
        Sui.Button(`type` = typings.react.reactStrings.submit)("Submit")
      ),
      Sui.Divider(horizontal = true)(
        Sui.Header(as = "h4")(
          Sui.Icon(name = SuiStrings.tag),
          "Card and Image"
        )
      ),
      Sui.Card(
        img(src := "https://react.semantic-ui.com/images/avatar/large/matthew.png"),
        Sui.CardContent(
          Sui.CardHeader()("Matthew"),
          Sui.CardMeta()(
            span(className := "date")("Joined in 2015")
          ),
          Sui.CardDescription()(
            "Matthew is a musician living in Nashville."
          )
        ),
        Sui.CardContent(extra = true)(
          a(
            Sui.Icon(name = SuiStrings.user),
            "22 Friends"
          )
        )
      )

    )
  }
}

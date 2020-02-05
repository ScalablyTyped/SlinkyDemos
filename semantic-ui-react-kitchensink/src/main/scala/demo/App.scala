package demo

import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.core.facade.Hooks._
import slinky.web.html._
import typings.semanticUiReact.{components => Sui, semanticUiReactStrings => SuiStrings}
import typings.semanticUiReact.genericMod.SemanticWIDTHSSTRING._

import scala.language.implicitConversions

@react object App {
  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>
    val (isModalVisible, updateIsModalVisible) = useState(false)

    div(
      Sui.Grid(
        Sui.GridColumn(width = `1`),
        Sui.GridColumn(width = `14`)(
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
            Sui.Image(size = SuiStrings.medium, wrapped = true, ui = false)(
              src := "https://react.semantic-ui.com/images/avatar/large/matthew.png"
            ),
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
          ),
          Sui.Divider(horizontal = true)(
            Sui.Header(as = "h4")(
              Sui.Icon(name = SuiStrings.tag),
              "Modal"
            )
          ),
          p(
            Sui.Button(primary = true, onClick = (_, _) => updateIsModalVisible(true))(
              "Show modal"
            )
          )
        ),
        Sui.GridColumn(width = `1`)
      ),
      Sui.Modal(
        onClose = (_, _) => updateIsModalVisible(false)
      )(
        open := isModalVisible
      )(
        Sui.ModalHeader("Select a Photo"),
        Sui.ModalContent(image = true)(
          Sui.Image(size = SuiStrings.medium, wrapped = true)(
            src := "https://react.semantic-ui.com/images/avatar/large/rachel.png"
          ),
          Sui.ModalDescription(
            Sui.Header("Default Profile Image"),
            p("We've found the following gravatar image associated with your e-mail address."),
            p("Is it okay to use this photo?")
          )
        )
      )
    )
  }
}

package demo.login

import demo.login.Styles.styles
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks
import slinky.web.html._
import typings.materialUiCore.components._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

//@JSImport("js/logo.svg", JSImport.Default)
//@js.native
//object Logo extends js.Object

@JSImport("resources/google.svg", JSImport.Default)
@js.native
object GoogleLogo extends js.Object


// https://github.com/flatlogic/react-material-admin/blob/master/src/pages/login/Login.js
@react object Login {

  type Props = Unit

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { _ =>
    val classes = styles(js.undefined)

    val (isLoading, setIsLoading)         = Hooks.useState(false);
    val (error, setError)                 = Hooks.useState(false);
    val (activeTabId, setActiveTabId)     = Hooks.useState(0);
    val (nameValue, setNameValue)         = Hooks.useState("");
    val (loginValue, setLoginValue)       = Hooks.useState("");
    val (passwordValue, setPasswordValue) = Hooks.useState("");

    Grid
      .container(true)
      .className(classes("container"))(
        div(className := classes("logotypeContainer"))(
          img(src := GoogleLogo.asInstanceOf[String],
            alt := "logo",
            className := classes("logotypeImage")),
          Typography.className(classes("logotypeText"))("Material Admin")
        ))
  }

}
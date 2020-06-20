package demo.login

import demo.login.Styles.styles
import org.scalablytyped.runtime.StringDictionary
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.{Fragment, Hooks}
import slinky.web.html._
import typings.materialUiCore.anon.{PartialClassNameMapInputC, PartialInputProps}
import typings.materialUiCore.components._
import typings.materialUiCore.materialUiCoreStrings.primary
import typings.materialUiCore.materialUiCoreStrings.large
import typings.materialUiCore.materialUiCoreStrings.normal
import typings.materialUiCore.materialUiCoreStrings.contained
import typings.materialUiCore.materialUiCoreStrings.secondary
import typings.materialUiCore.typographyTypographyMod.Style
import typings.classnames.{mod => classNames}
import typings.std.{HTMLInputElement, HTMLTextAreaElement}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("./logo.svg", JSImport.Default)
@js.native
object Logo extends js.Object

@JSImport("./google.svg", JSImport.Default)
@js.native
object GoogleLogo extends js.Object

// https://github.com/flatlogic/react-material-admin/blob/master/src/pages/login/Login.js
@react object Login {

  type Props = Unit

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { _ =>
    val classes = styles(js.undefined)

    val (isLoading, setIsLoading) = Hooks.useState(false);
    val (error, setError) = Hooks.useState(false);
    val (activeTabId, setActiveTabId) = Hooks.useState(0);
    val (nameValue, setNameValue) = Hooks.useState("");
    val (loginValue, setLoginValue) = Hooks.useState("");
    val (passwordValue, setPasswordValue) = Hooks.useState("");

    Grid
      .container(true)
      .className(classes("container"))(
        div(className := classes("logotypeContainer"))(
          img(src := Logo.asInstanceOf[String], alt := "logo", className := classes("logotypeImage")),
          Typography.className(classes("logotypeText"))("Material Admin")
        ),
        div(className := classes("formContainer"))(
          div(className := classes("form"))(
            Tabs(activeTabId)
              .onChange((_, id) => setActiveTabId(id.asInstanceOf[Int]))
              .indicatorColor(primary)
              .textColor(primary)
              .centered(true)(
                Tab.label("Login").className(classNames(StringDictionary[js.Any]("root" -> classes("tab")))),
                Tab.label("New User").className(classNames(StringDictionary[js.Any]("root" -> classes("tab"))))
              ),
            activeTabId match {
              case 0 =>
                Fragment(
                  Typography
                    .variant(Style.h1)
                    .className(classes("greeting"))("Good Morning, User"),
                  Button
                    .size(large)
                    .className(classes("googleButton"))(
                      img(src := GoogleLogo.asInstanceOf[String], alt := "google", className := classes("googleIcon")),
                      "Sign in with Google"
                    ),
                  div(className := classes("formDividerContainer"))(
                    div(className := classes("formDivider")),
                    Typography.className(classes("formDividerWord"))("or"),
                    div(className := classes("formDivider"))
                  ),
                  Fade.in(error)(
                    Typography
                      .color(secondary)
                      .className(classes("errorMessage"))("Something is wrong with your login or password :(")
                  ),
                  TextField.StandardTextFieldProps
                    .id("email")
                    .InputProps(
                      PartialInputProps()
                        .setClasses(
                          PartialClassNameMapInputC()
                            .setUnderline(classes("textFieldUnderline"))
                            .setInput(classes("textField"))
                        )
                    )
                    .value(loginValue)
                    .onChange(e => setLoginValue(e.currentTarget.asInstanceOf[HTMLInputElement].value))
                    .margin(normal)
                    .placeholder("Email Address")
                    .`type`("email")
                    .fullWidth(true),
                  TextField.StandardTextFieldProps
                    .id("password")
                    .InputProps(
                      PartialInputProps()
                        .setClasses(
                          PartialClassNameMapInputC()
                            .setUnderline(classes("textFieldUnderline"))
                            .setInput(classes("textField"))
                        )
                    )
                    .value(passwordValue)
                    .onChange(e => setPasswordValue(e.currentTarget.asInstanceOf[HTMLInputElement].value))
                    .margin(normal)
                    .placeholder("Password")
                    .`type`("password")
                    .fullWidth(true),
                  div(className := classes("formButtons"))(
                    if (isLoading)
                      CircularProgress.size(26).className(classes("loginLoader"))
                    else
                      Button
                        .disabled(loginValue.length == 0 || passwordValue.length == 0)
                        .variant(contained)
                        .color(primary)
                        .size(large)("Login"),
                    Button
                      .color(primary)
                      .size(large)
                      .className(classes("forgetButton"))("Forget Password")
                  )
                )
              case 1 =>
                Fragment(
                  Typography
                    .variant(Style.h1)
                    .className(classes("greeting"))("Welcome"),
                  Typography.variant(Style.h2).className(classes("subGreeting"))("Create your account"),
                  Fade.in(error)(
                    Typography
                      .color(secondary)
                      .className(classes("errorMessage"))("Something is wrong with your login or password :(")
                  ),
                  TextField.StandardTextFieldProps
                    .id("name")
                    .InputProps(
                      PartialInputProps()
                        .setClasses(
                          PartialClassNameMapInputC()
                            .setUnderline(classes("textFieldUnderline"))
                            .setInput(classes("textField"))
                        )
                    )
                    .value(nameValue)
                    .onChange(e => setNameValue(e.currentTarget.asInstanceOf[HTMLInputElement].value))
                    .margin(normal)
                    .placeholder("Full Name")
                    .`type`("text")
                    .fullWidth(true),
                  TextField.StandardTextFieldProps
                    .id("email")
                    .InputProps(
                      PartialInputProps()
                        .setClasses(
                          PartialClassNameMapInputC()
                            .setUnderline(classes("textFieldUnderline"))
                            .setInput(classes("textField"))
                        )
                    )
                    .value(loginValue)
                    .onChange(e => setLoginValue(e.currentTarget.asInstanceOf[HTMLInputElement].value))
                    .margin(normal)
                    .placeholder("Email Address")
                    .`type`("email")
                    .fullWidth(true),
                  TextField.StandardTextFieldProps
                    .id("password")
                    .InputProps(
                      PartialInputProps()
                        .setClasses(
                          PartialClassNameMapInputC()
                            .setUnderline(classes("textFieldUnderline"))
                            .setInput(classes("textField"))
                        )
                    )
                    .value(passwordValue)
                    .onChange(e => setPasswordValue(e.currentTarget.asInstanceOf[HTMLInputElement].value))
                    .margin(normal)
                    .placeholder("Password")
                    .`type`("password")
                    .fullWidth(true),
                  div(className := classes("formButtons"))(
                    if (isLoading)
                      CircularProgress.size(26).className(classes("loginLoader"))
                    else
                      Button
                        .disabled(loginValue.length == 0 || passwordValue.length == 0)
                        .size(large)
                        .variant(contained)
                        .color(primary)
                        .fullWidth(true)
                        .className(classes("createAccountButton"))("Create your account")
                  ),
                  div(className := classes("formDividerContainer"))(
                    div(className := classes("formDivider")),
                    Typography.className(classes("formDividerWord"))("or"),
                    div(className := classes("formDivider"))
                  ),
                  Button
                    .color(primary)
                    .size(large)
                    .className(
                      classNames(
                        StringDictionary[js.Any](
                          classes("googleButton") -> true,
                          classes("googleButtonCreating") -> true
                        )
                      )
                    )(
                      img(src := GoogleLogo.asInstanceOf[String], alt := "google", className := classes("googleIcon")),
                      "Sign in with Google"
                    )
                )
            }
          ),
          Typography
            .color(primary)
            .className(classes("copyright"))("© 2014-2019 Flatlogic, LLC. All rights reserved.")
        )
      )
  }

}

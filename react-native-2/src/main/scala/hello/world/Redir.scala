package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks.useState
import slinky.native.{ScrollView, Text, View}

import typings.reactDashRouterDashNative.ReactRouterNativeFacade._

@react object Redir {

  case class Props(redirPath: String, stayPath: String)

  val component = FunctionalComponent[Props] { props =>

    if (props.redirPath != props.stayPath)
      Redirect(RedirectProps(to = props.redirPath))
    else
      View()
  }
}

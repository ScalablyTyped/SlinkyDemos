package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.native.{Text, View}

import typings.reactDashRouterDashNative.ReactRouterNativeFacade._
import typings.reactDashRouter.reactDashRouterMod._

@react object ReactRouter extends Redirectable {

  case class Props(redirPath: String, `match`: `match`[Unit])

  val component = FunctionalComponent[Props] { props =>
    def link(title: String, path: String) =
      Link(LinkProps(to = props.`match`.url + path, style = Styles.subNavItemStyle))(
        Text(style      = Styles.topicStyle)(title)
      )

    checkRedirection(
      props.redirPath,
      props.`match`.path,
      View(
        Text(
          style = Styles.title
        )("React Router demo"),
        Text(style = Styles.headerStyle)("Topics"),
        View(
          link("Rendering with React", "/rendering"),
          link("Components", "/components"),
          link("Props v. State", "/props-v-state")
        ),
        Route[Topic.Param](path = props.`match`.path + "/:topicId", render = props => Topic(props.`match`)),
        Route[Unit](path        = props.`match`.path, render               = _ => Text("Please select a topic"), exact = true)
      )
    )
  }
}

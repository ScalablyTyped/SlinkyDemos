package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.native.{Text, View}

import typingsSlinky.reactDashRouterDashNative.components._
import typingsSlinky.reactDashRouter.reactDashRouterMod._

@react object ReactRouter extends Redirectable {

  case class Props(redirPath: String, `match`: `match`[_])

  val component = FunctionalComponent[Props] { props =>
    def link(title: String, path: String): ReactElement =
      Link(to = props.`match`.url + path, style = Styles.subNavItemStyle)(
        Text(style = Styles.topicStyle)(title)
      )

    checkRedirection(
      props.redirPath,
      props.`match`.path,
      View(
        Text(style = Styles.title)("React Router demo": ReactElement),
        Text(style = Styles.headerStyle)("Topics"),
        View(
          link("Rendering with React", "/rendering"),
          link("Components", "/components"),
          link("Props v. State", "/props-v-state")
        ),
        Route(
          RouteProps(
            path   = props.`match`.path + "/:topicId",
            render = props => Topic(props.`match`.asInstanceOf[`match`[Topic.Param]])
          )
        ),
        Route(RouteProps(path = props.`match`.path, render = _ => Text("Please select a topic"), exact = true))
      )
    )
  }
}

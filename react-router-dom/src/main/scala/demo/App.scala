package demo

import slinky.core._
import slinky.core.annotations.react
import slinky.web.html._
import typingsSlinky.reactDashRouter.reactDashRouterMod._
import typingsSlinky.reactDashRouterDashDom.components._

import scala.scalajs.js

@react object App {

  type Props = Unit

  def home = div(h2("Home"))

  def about = div(h2("About"))

  val component = FunctionalComponent[Props] { _ =>
    val renderIntro = div(
      header(className := "App-header")(h1(className := "App-title")("Welcome to React (with Scala.js!)")),
      p(className := "App-intro")("To get started, edit ", code("App.scala"), " and save to reload.")
    )

    div(className := "App")(
      renderIntro,
      BrowserRouter(
        div(
          ul(
            li(Link[js.Object](to = "/")("Home")),
            li(Link[js.Object](to = "/about")("About")),
            li(Link[js.Object](to = "/topics")("Topics"))
          ),
          hr(),
          Route(RouteProps(exact = true, path = "/", render = _ => home)),
          Route(RouteProps(path = "/about", render = _ => about)),
          Route(RouteProps(path = "/topics", render = props => Topics(props.`match`)))
        )
      )
    )
  }
}

@react object Topics {

  type Props = `match`[_]

  val component = FunctionalComponent[Props] { m =>
    div(
      h2("Topics"),
      ul(
        li(Link[js.Object](to = m.url + "/rendering")("Rendering with React")),
        li(Link[js.Object](to = m.url + "/components")("Components")),
        li(Link[js.Object](to = m.url + "/props-v-state")("Props v. State"))
      ),
      hr(),
      Route(
        RouteProps(
          path = m.path + "/:topicId",
          render = props => Topic(props.`match`.asInstanceOf[`match`[Topic.Param]])
        )
      ),
      Route(RouteProps(exact = true, path = m.path, render = _ => h3("Please select a topic")))
    )
  }
}

@react object Topic {

  @js.native
  trait Param extends js.Object {
    val topicId: String = js.native
  }

  case class Props(`match`: `match`[Topic.Param])

  val component = FunctionalComponent[Props] { props =>
    div(
      h3("Topic: " + props.`match`.params.topicId)
    )
  }
}

package demo

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._
import demo.ReduxFacade.Connected
import typingsSlinky.semanticUiReact.{AnonMenuItem => TabStructure, components => Sui, semanticUiReactStrings => SuiStrings
}

import scala.scalajs.js

@react
object Demo {

  class Props(val title: String) extends js.Object

  val CardDemo: ReactElement =
    Sui.Card(color = SuiStrings.orange)(
      Sui.CardHeader("CardHeader"),
      Sui.CardMeta("CardMeta"),
      Sui.Divider(),
      Sui.Search(minCharacters = 1)
    )

  val ProgressDemo: ReactElement =
    Sui.Card(
      Sui.Progress(percent = 70, warning = true),
      Sui.Progress(percent = 100, warning = false)
    )

  val component = FunctionalComponent[Connected[GithubSearch.State, GithubSearch.SearchAction] with Props](props =>
    div(
      Sui.Header(size = SuiStrings.large)(props.title),
      Sui.Tab(
        panes = js.Array(
          TabStructure(
            menuItem = "Repo search",
            render = () => GithubSearch(props.state, props.dispatch)
          ),
          TabStructure(menuItem = "Card", render = () => CardDemo),
          TabStructure(menuItem = "Progress", render = () => ProgressDemo)
        )
      )
    )
  )
}

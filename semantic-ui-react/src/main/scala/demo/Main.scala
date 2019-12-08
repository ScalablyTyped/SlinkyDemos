package demo

import slinky.core.ExternalComponent
import slinky.web.ReactDOM
import typingsSlinky.react.reactMod._
import typingsSlinky.reactDashRedux.ReduxFacade
import typingsSlinky.reactDashRedux.components.Provider
import typingsSlinky.redux.reduxMod.{createStore, Store}
import typingsSlinky.reduxDashDevtoolsDashExtension.reduxDashDevtoolsDashExtensionMod.{
  devToolsEnhancer,
  EnhancerOptions
}
import typingsSlinky.std.window

object Main {

  def main(argv: Array[String]): Unit = {
    val container = window.document.getElementById("container")
    /* set up redux store with devtools*/
    val Store: Store[GithubSearch.State, GithubSearch.SearchAction] =
      createStore(GithubSearch.Reducer, devToolsEnhancer(EnhancerOptions(name = "github search store")))

    /* Connect `Demo` component */
    val ConnectedDemo: ExternalComponent { type Props = Demo.Props } =
      ReduxFacade.simpleConnect(Store, Demo.component)

    /* And use `Provider` instead of just passing a normal, goddamn parameter */
    ReactDOM.render(
      Provider[GithubSearch.SearchAction](Store)(ConnectedDemo(new Demo.Props("Welcome"))),
      container
    )
  }
}

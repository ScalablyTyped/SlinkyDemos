package demo

import org.scalajs.dom.window.document
import slinky.core.ExternalComponent
import slinky.web.ReactDOM
import typingsSlinky.reactDashRedux.components.Provider
import typingsSlinky.redux.reduxMod.{createStore, Store}
import typingsSlinky.reduxDashDevtoolsDashExtension.reduxDashDevtoolsDashExtensionMod.{
  devToolsEnhancer,
  EnhancerOptions
}

object Main {

  def main(argv: Array[String]): Unit = {
    val container = document.getElementById("container")
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

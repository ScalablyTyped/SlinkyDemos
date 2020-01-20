package demo

import org.scalajs.dom.window.document
import slinky.core.ExternalComponent
import slinky.web.ReactDOM
import typingsSlinky.reactRedux.components.Provider
import typingsSlinky.redux.mod.{createStore, Store}
import typingsSlinky.reduxDevtoolsExtension.mod.{devToolsEnhancer, EnhancerOptions}

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

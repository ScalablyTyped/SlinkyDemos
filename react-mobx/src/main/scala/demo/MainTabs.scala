package demo

import org.scalajs.dom.console
import slinky.core.annotations.react
import slinky.core.{FunctionalComponent, ObservingFC}
import slinky.web.html._
import typings.csstype.mod.BackgroundColorProperty
import typings.materialUi.lightBaseThemeMod.{default => theme}
import typings.materialUi.stylesMod.{getMuiTheme, MuiTheme}
import typings.materialUi.{components => Mui}
import typings.react.mod.CSSProperties

import scala.scalajs.js

@react
object MainTabs {

  case class Props(testStore: MobXTest.Store, githubStore: GithubSearch.Store)

  val component: FunctionalComponent[Props] = ObservingFC[Props] {
    case Props(testStore, githubStore) =>
      val effectiveTheme = getMuiTheme(new MuiTheme {
        rawTheme = js.defined(theme)
        palette = theme.palette
      })

      Mui.MuiThemeProvider(effectiveTheme)(
        div(
          onClick := (e => console.warn("onclick", e)),
          style := new CSSProperties {
            backgroundColor = theme.palette.flatMap(_.canvasColor).asInstanceOf[js.UndefOr[BackgroundColorProperty]]
            width = 800
            height = 800
           }
        )(
          Mui.Tabs(
            Mui.Tab(label = "Github search")(GithubSearch(githubStore)),
            Mui.Tab(label = "MobX")(MobXTest(testStore))
          )
        )
      )
  }
}

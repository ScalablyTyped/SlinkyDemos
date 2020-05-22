package demo

import org.scalajs.dom.console
import slinky.core.annotations.react
import slinky.core.{FunctionalComponent, ObservingFC}
import slinky.web.html._
import typings.materialUi.MaterialUI.Styles.MuiTheme
import typings.materialUi.lightBaseThemeMod.{default => theme}
import typings.materialUi.stylesMod.getMuiTheme
import typings.materialUi.{components => Mui}
import typings.react.mod.CSSProperties

@react
object MainTabs {

  case class Props(testStore: MobXTest.Store, githubStore: GithubSearch.Store)

  val component: FunctionalComponent[Props] = ObservingFC[Props] {
    case Props(testStore, githubStore) =>
      val effectiveTheme = getMuiTheme(MuiTheme().setRawTheme(theme).setPalette(theme.palette.orNull))

      Mui.MuiThemeProvider.muiTheme(effectiveTheme)(
        div(
          onClick := (e => console.warn("onclick", e)),
          style := CSSProperties()
            .setBackgroundColor(theme.palette.flatMap(_.canvasColor).orNull[String])
            .setMinWidth(800)
            .setHeight(800)
        )(
          Mui.Tabs(
            Mui.Tab.label("Github search")(GithubSearch(githubStore)),
            Mui.Tab.label("MobX")(MobXTest(testStore))
          )
        )
      )
  }
}

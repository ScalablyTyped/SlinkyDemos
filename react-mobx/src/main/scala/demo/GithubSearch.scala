package demo

import org.scalablytyped.runtime.StringDictionary
import org.scalajs.dom.{console, window}
import slinky.core.annotations.react
import slinky.core.{FunctionalComponent, ObservingFC}
import slinky.web.html._
import typings.axios.mod.{AxiosError, AxiosRequestConfig, AxiosResponse, default => Axios}
import typings.csstype.csstypeStrings
import typings.materialUi.{components => Mui}
import typings.mobx.observablevalueMod.IObservableValue
import typings.mobx.{mod => mobx}
import typings.react.mod.CSSProperties

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.util.{Failure, Success}

@react
object GithubSearch {

  case class Props(store: Store)

  trait Repository extends js.Object {
    val description: String
    val forks_count: Int
    val name: String
    val stargazers_count: Int
    val html_url: String
  }

  trait Response extends js.Object {
    val items: js.Array[Repository]
  }

  class Store {
    val search: IObservableValue[String] =
      mobx.observable.box("ScalablyTyped")

    val result: IObservableValue[Option[js.Array[Repository]]] =
      mobx.observable.box(None)

    val searchForRepos: js.Function0[Unit] =
      mobx.action(
        "searchForRepos",
        () =>
          Axios
            .get[Response, AxiosResponse[Response]](
              "https://api.github.com/search/repositories",
              AxiosRequestConfig()
                .setParams(StringDictionary("q" -> search.get(), "sort" -> "stars"))
                .setHeaders(StringDictionary("Accept" -> "application/vnd.github.v3+json"))
            )
            .toFuture
            .onComplete {
              case Failure(js.JavaScriptException(err)) =>
                val axiosError = err.asInstanceOf[AxiosError[js.Any]]
                console.warn("request rejected", axiosError.response)
              case Failure(other) =>
                console.warn("request failed", other.getMessage)
              case Success(res) =>
                console.warn("got data", res.data.items)
                result.set(Some(res.data.items))
            }
      )
  }

  def gotoRepo(repo: Repository): Unit = window.location.href = repo.html_url

  /* this is a simple functional component to display a github repo in a table row */
  val RepoRow: FunctionalComponent[Repository] = FunctionalComponent(repo =>
    Mui.TableRow(
      Mui.TableRowColumn(repo.name),
      Mui.TableRowColumn(repo.forks_count),
      Mui.TableRowColumn(repo.stargazers_count),
      Mui.TableRowColumn(
        Mui.FlatButton
          .onClick(_ => gotoRepo(repo))
          .disabled(false)(
            "Go to project"
          )
      )
    )
  )

  val component: FunctionalComponent[Props] = ObservingFC[Props] {
    case Props(store) =>
      div(
        Mui.Paper
          .rounded(true)
          .style(
            CSSProperties()
              .setHeight("100px")
              .setDisplay(csstypeStrings.flex)
              .setAlignItems(csstypeStrings.center)
              .setJustifyContent(csstypeStrings.center)
          ),
        Mui.TextField
          .onChange((_, newValue) => store.search.set(newValue))
          .name("search")
          .value(store.search.get),
        Mui.FlatButton.onClick(_ => store.searchForRepos())("Search"),
        store.result.get() match {
          case None => div("No result yet")
          case Some(repos) =>
            Mui.Table(
              Mui.TableHeader(
                Mui.TableRow(
                  Mui.TableRowColumn("name"),
                  Mui.TableRowColumn("forks_count"),
                  Mui.TableRowColumn("stargazers_count"),
                  Mui.TableRowColumn("link")
                )
              ),
              Mui.TableBody(repos.to(Seq).map(repo => RepoRow(repo).withKey(repo.name)))
            )
        }
      )
  }
}

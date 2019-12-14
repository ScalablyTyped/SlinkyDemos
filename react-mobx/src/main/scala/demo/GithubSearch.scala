package demo

import org.scalajs.dom.{console, window}
import slinky.core.annotations.react
import slinky.core.{FunctionalComponent, ObservingFC, TagMod}
import slinky.web.html._
import typingsSlinky.axios.axiosMod.{AxiosError, AxiosRequestConfig, AxiosResponse, default => Axios}
import typingsSlinky.csstype.csstypeStrings
import typingsSlinky.materialDashUi.{components => Mui}
import typingsSlinky.mobx.libTypesObservablevalueMod.IObservableValue
import typingsSlinky.mobx.{mobxMod => MobX}
import typingsSlinky.react.reactMod._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.util.{Failure, Success}

@react
object GithubSearch {

  case class Props(store: Store)

  trait Repository extends js.Object {
    val description:      String
    val forks_count:      Int
    val name:             String
    val stargazers_count: Int
    val html_url:         String
  }

  trait Response extends js.Object {
    val items: js.Array[Repository]
  }

  class Store {
    val search: IObservableValue[String] =
      MobX.observable.box("ScalablyTyped")

    val result: IObservableValue[js.UndefOr[js.Array[Repository]]] =
      MobX.observable.box(js.undefined)

    val searchForRepos: js.Function0[Unit] =
      MobX.action(
        "searchForRepos",
        () =>
          Axios
            .get[Response, AxiosResponse[Response]](
              "https://api.github.com/search/repositories",
              AxiosRequestConfig(
                params  = js.Dynamic.literal(q      = search.get(), sort = "stars"),
                headers = js.Dynamic.literal(Accept = "application/vnd.github.v3+json")
              )
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
                result.set(res.data.items)
            }
      )
  }

  def gotoRepo(repo: Repository): () => Unit = () => window.location.href = repo.html_url

  /* this is a simple functional component to display a github repo in a table row */
  val RepoRow = FunctionalComponent[Repository](
    repo =>
      Mui.TableRow()(
        Mui.TableRowColumn()(repo.name),
        Mui.TableRowColumn()(repo.forks_count),
        Mui.TableRowColumn()(repo.stargazers_count),
        Mui.TableRowColumn()(
          Mui.FlatButton()(onClick := gotoRepo(repo), disabled := false)(
            "Go to project"
          )
        )
      )
  )

  val component: FunctionalComponent[Props] = ObservingFC[Props] {
    case Props(store) =>
      div(
        Mui.Paper(
          style = new CSSProperties {
            height         = "100px"
            display        = csstypeStrings.flex
            alignItems     = csstypeStrings.center
            justifyContent = csstypeStrings.center
          },
          rounded = true
        )(),
        Mui.TextField(onChange = (_, newValue) => store.search.set(newValue))(
          name := "search",
          value := store.search.get
        ),
        Mui.FlatButton()(onClick := store.searchForRepos)("Search"),
        store.result
          .get()
          .fold[TagMod[Any]](div("No result yet"))(
            repos =>
              Mui.Table()(
                Mui.TableHeader()(
                  Mui.TableRow()(
                    Mui.TableRowColumn()("name"),
                    Mui.TableRowColumn()("forks_count"),
                    Mui.TableRowColumn()("stargazers_count"),
                    Mui.TableRowColumn()("link")
                  )
                ),
                Mui.TableBody()(
                  repos.to(Seq).map(repo => RepoRow(repo).withKey(repo.name)): _*
                )
              )
          )
      )
  }
}

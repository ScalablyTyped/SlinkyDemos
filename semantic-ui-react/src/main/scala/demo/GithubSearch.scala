package demo

import org.scalajs.dom.experimental._
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._
import demo.ReduxFacade.Extractor
import typingsSlinky.redux.reduxMod.{Action, Dispatch, Reducer}
import typingsSlinky.semanticDashUiDashReact.distCommonjsElementsIconIconMod.{IconProps, IconSizeProp}
import typingsSlinky.semanticDashUiDashReact.distCommonjsGenericMod.{SemanticICONS, SemanticVERTICALALIGNMENTS}
import typingsSlinky.semanticDashUiDashReact.{components => Sui}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js

@react
object GithubSearch {

  object api {
    trait Repository extends js.Object {
      val description:      String
      val forks_count:      Int
      val name:             String
      val full_name:        String
      val stargazers_count: Int
      val html_url:         String
    }

    trait Response extends js.Object {
      val items: js.Array[Repository]
    }

    trait GithubError extends js.Object {
      val message:           String
      val documentation_url: String
    }

    def doSearch(search: String): Future[Either[GithubError, Response]] =
      Fetch.fetch(
        s"https://api.github.com/search/repositories?q=$search&sort=stars",
        new RequestInit {
          override var headers: js.UndefOr[HeadersInit] = js.defined(
            js.Array(js.Array("Accept", "application/vnd.github.v3+json"))
          )
          override var method:         js.UndefOr[HttpMethod]         = js.undefined
          override var body:           js.UndefOr[BodyInit]           = js.undefined
          override var referrer:       js.UndefOr[String]             = js.undefined
          override var referrerPolicy: js.UndefOr[ReferrerPolicy]     = js.undefined
          override var mode:           js.UndefOr[RequestMode]        = js.undefined
          override var credentials:    js.UndefOr[RequestCredentials] = js.undefined
          override var cache:          js.UndefOr[RequestCache]       = js.undefined
          override var redirect:       js.UndefOr[RequestRedirect]    = js.undefined
          override var integrity:      js.UndefOr[String]             = js.undefined
          override var keepalive:      js.UndefOr[Boolean]            = js.undefined
          override var signal:         js.UndefOr[AbortSignal]        = js.undefined
          override var window:         js.UndefOr[Null]               = js.undefined
        }
      ).toFuture.flatMap {
        case res if res.status == 200 =>
          res.json().toFuture.map(data => Right(data.asInstanceOf[Response]))
        case errorRes =>
          errorRes.json().toFuture.map(data => Left(data.asInstanceOf[GithubError]))
      }

  }

  trait State extends js.Object {
    val search: String
    val repos:  js.UndefOr[js.Array[api.Repository]]
    val error:  js.UndefOr[api.GithubError]
  }

  object State {
    val initial = State("ScalablyTyped", js.undefined, js.undefined)

    def apply(
        _search: String,
        _repos:  js.UndefOr[js.Array[api.Repository]],
        _error:  js.UndefOr[api.GithubError]
    ): State =
      new State {
        val search = _search
        val repos  = _repos
        val error  = _error
      }
  }

  sealed trait SearchAction extends Action[String]

  trait SearchTextChanged extends SearchAction {
    val value: String
  }

  object SearchTextChanged extends Extractor[SearchTextChanged] {
    protected val _type = "SEARCH_TEXT_CHANGED"

    def apply(_value: String): SearchTextChanged =
      new SearchTextChanged {
        var `type` = _type
        val value  = _value
      }
  }

  trait SearchReposSuccess extends SearchAction {
    val repos: js.Array[api.Repository]
  }

  object SearchReposSuccess extends Extractor[SearchReposSuccess] {
    protected val _type = "SEARCH_REPOS_SUCCESS"

    def apply(_repos: js.Array[api.Repository]): SearchReposSuccess =
      new SearchReposSuccess {
        var `type` = _type
        val repos  = _repos
      }
  }

  trait SearchReposFailure extends SearchAction {
    val error: api.GithubError
  }

  object SearchReposFailure extends Extractor[SearchReposFailure] {
    protected val _type = "SEARCH_REPOS_FAILURE"

    def apply(_error: api.GithubError): SearchReposFailure =
      new SearchReposFailure {
        var `type` = _type
        val error  = _error
      }
  }

  val Reducer: Reducer[State, SearchAction] = (stateOpt, action) => {
    val state = stateOpt.getOrElse(State.initial)
    action match {
      case SearchTextChanged(x)  => State(x.value, state.repos, js.undefined)
      case SearchReposSuccess(x) => State(state.search, x.repos, js.undefined)
      case SearchReposFailure(x) => State(state.search, js.undefined, x.error)
      case _                     => state
    }
  }

  case class Props(state: State, dispatch: Dispatch[SearchAction])

  val component = FunctionalComponent[Props] { props =>
    div(
      Sui.Input(onChange = (_, data) => props.dispatch(SearchTextChanged(data.value_InputOnChangeData)))(
        defaultValue := props.state.search
      ),
      input(),
      Sui.Button(
        icon = IconProps(name = SemanticICONS.`search plus`),
        onClick = (e, data) =>
          api.doSearch(props.state.search).foreach {
            case Right(res)        => props.dispatch(SearchReposSuccess(res.items))
            case Left(githubError) => props.dispatch(SearchReposFailure(githubError))
          }
      ),
      props.state.error.toOption.map(e => div(e.message)),
      props.state.repos.toOption.map(
        repos =>
          Sui.List(divided = true, relaxed = true)(
            repos.to(Seq).map[ReactElement](
              repo =>
                Sui
                  .ListItem()
                  .withKey(repo.name)(
                    Sui.ListIcon(
                      name          = SemanticICONS.github,
                      size          = IconSizeProp.large,
                      verticalAlign = SemanticVERTICALALIGNMENTS.middle
                    ),
                    Sui.ListContent(Sui.ListHeader(content = a(href := repo.html_url))(repo.full_name)),
                    Sui.ListDescription(repo.description)
                  )
            ): _*
          )
      )
    )
  }
}

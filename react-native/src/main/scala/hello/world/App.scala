package hello.world

import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.Hooks.useState
import slinky.core.facade.ReactElement
import slinky.native.ScrollView
import typings.antDesignReactNative.PartialLocale
import typings.antDesignReactNative.antDesignReactNativeStrings.xl
import typings.antDesignReactNative.components.{List => AntdList, _}
import typings.bang88ReactNativeDrawerLayout.mod.DrawerLayout
import typings.reactNative.mod.{NativeTouchEvent, NodeHandle}
import typings.reactRouter.components.Route
import typings.reactRouter.mod.RouteProps
import typings.reactRouterNative.components.{NativeRouter, Redirect}

import scala.scalajs.js.|

@react object App {
  sealed abstract class RoutePath(val path: String, val title: String)

  object RoutePath {
    object Home extends RoutePath("/", "Home")
    object Antd extends RoutePath("/antd", "Antd")
    object ReactRouter extends RoutePath("/react_router", "React Router")

    val allOrdered: List[RoutePath] = List(Home, Antd, ReactRouter)
  }

  type Props = Unit

  def toOption[T](ot: T | Null): Option[T] =
    Option(ot.asInstanceOf[T])

  val component = FunctionalComponent[Props] { _ =>
    var ref: Option[DrawerLayout] = None

    val (redirPath, updateRedirPath) = useState(RoutePath.Home.path)

    def navigateTo(route: RoutePath)(e: SyntheticEvent[NodeHandle, NativeTouchEvent]): Unit = {
      updateRedirPath(route.path)
      ref.foreach(_.closeDrawer())
    }

    def checkRedirection(stayPath: String, elem: ReactElement): ReactElement =
      if (redirPath != stayPath) Redirect(to = redirPath) else elem

    val routeLinks = RoutePath.allOrdered.zipWithIndex.map {
      case (route, index) => ListItem.onPress(navigateTo(route))(Text(route.title)).withKey(index.toString)
    }

    Provider.locale(PartialLocale(locale = "enUS"))(
      NativeRouter(
        Drawer
          .drawerRef(nullableRef => ref = toOption(nullableRef))
          .sidebar(ScrollView(WhiteSpace.size(xl), AntdList(routeLinks)))(
            AntdList.renderHeader(WhiteSpace.size(xl): ReactElement)(
              ListItem.extra(Icon(name = "menu")).onPress(e => ref.foreach(_.openDrawer()))("React Native demo")
            )
          ),
        Route(
          RouteProps(
            path = RoutePath.Home.path,
            render = props => checkRedirection(props.`match`.path, Home()),
            exact = true
          )
        ),
        Route(
          RouteProps(
            path = RoutePath.Antd.path,
            render = props => checkRedirection(props.`match`.path, Antd())
          )
        ),
        Route(
          RouteProps(
            path = RoutePath.ReactRouter.path,
            render = props => checkRedirection(props.`match`.path, ReactRouter(props.`match`))
          )
        )
      )
    )
  }
}

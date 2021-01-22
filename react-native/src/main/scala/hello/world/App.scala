package hello.world

import slinky.core._
import slinky.core.annotations.react
import slinky.native.{SafeAreaView, ScrollView}
import typings.antDesignReactNative.anon.PartialLocale
import typings.antDesignReactNative.components.{List => AntdList, _}
import typings.bang88ReactNativeDrawerLayout.mod.DrawerLayout
import typings.reactRouter.components.Route
import typings.reactRouter.mod.RouteProps
import typings.reactRouterNative.components.{BackButton, NativeRouter}

import scala.scalajs.js.|

@react object App {
  sealed abstract class RoutePath(val path: String, val title: String)

  object RoutePath {
    object Home extends RoutePath("/", "Home")
    object Antd extends RoutePath("/antd", "Antd")
    object PlayAudio extends RoutePath("/audio", "PlayAudio")
    object Duration extends RoutePath("/duration", "Duration")
    object Webview extends RoutePath("/webview", "Webview")
    object ReactRouter extends RoutePath("/react_router", "React Router")

    val allOrdered: List[RoutePath] = List(Home, Antd, PlayAudio, Webview, Duration, ReactRouter)
  }

  type Props = Unit

  def toOption[T](ot: T | Null): Option[T] =
    Option(ot.asInstanceOf[T])

  val component = FunctionalComponent[Props] { _ =>
    var ref: Option[DrawerLayout] = None

    Provider.locale(PartialLocale().setLocale("enUS"))(
      NativeRouter(
        BackButton(
          SafeAreaView(style = Styles.container)(
            Drawer
              .drawerRef(nullableRef => ref = toOption(nullableRef))
              .sidebar(ScrollView(AntdList(MenuList(() => ref.foreach(_.closeDrawer())))))(
                AntdList(
                  ListItem.extra(Icon(name = "menu")).onPress(e => ref.foreach(_.openDrawer()))("React Native demo")
                )
              )(
                Route(
                  RouteProps()
                    .setPath(RoutePath.Home.path)
                    .setRender(_ => Home())
                    .setExact(true)
                ),
                Route(
                  RouteProps()
                    .setPath(RoutePath.Antd.path)
                    .setRender(_ => Antd())
                ),
                Route(
                  RouteProps()
                    .setPath(RoutePath.PlayAudio.path)
                    .setRender(_ => PlayAudio())
                ),
                Route(
                  RouteProps()
                    .setPath(RoutePath.Webview.path)
                    .setRender(_ => Webview())
                ),
                Route(
                  RouteProps()
                    .setPath(RoutePath.Duration.path)
                    .setRender(_ => Duration())
                ),
                Route(
                  RouteProps()
                    .setPath(RoutePath.ReactRouter.path)
                    .setRender(props => ReactRouter(props.`match`))
                )
              )
          )
        )
      )
    )
  }
}

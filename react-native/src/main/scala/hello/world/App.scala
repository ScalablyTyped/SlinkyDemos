package hello.world

import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.core.facade.Hooks._

import scala.scalajs.js.|
import typingsSlinky.atAntDashDesignReactDashNative.PartialLocale
import typingsSlinky.atAntDashDesignReactDashNative.components.{List => AntdList, _}
import typingsSlinky.atBang88ReactDashNativeDashDrawerDashLayout.atBang88ReactDashNativeDashDrawerDashLayoutMod.{DrawerLayout, default}
import typingsSlinky.atAntDashDesignReactDashNative.{atAntDashDesignReactDashNativeStrings => antdStrings}
import typingsSlinky.reactDashNative.components.ScrollView
import typingsSlinky.reactDashRouter.reactDashRouterMod.RouteProps
import typingsSlinky.reactDashRouter.components.Route
import typingsSlinky.reactDashRouterDashNative.components.NativeRouter

@react object App {

  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>
    var ref: default | Null = new default {}

    val (redirPath, updateRedirPath) = useState(RoutePath.HOME.path)

    val menus = RoutePath.allOrdered.indices.map(
      index =>
        ListItem(
          onPress = () => {
            updateRedirPath(RoutePath.allOrdered(index).path)
            ref.asInstanceOf[DrawerLayout].closeDrawer()
          }
        )(
          RoutePath.allOrdered(index).title
        ).withKey(index.toString)
    )

    Provider(locale = PartialLocale(locale = "enUS"))(
      Drawer(
        drawerRef = (ref = _),
        sidebar   = ScrollView()(WhiteSpace(size = antdStrings.xl), AntdList()(menus))
      )(
        NativeRouter()(
          View()(
            AntdList(renderHeader = WhiteSpace(size = antdStrings.xl): ReactElement)(
              ListItem(
                extra = Icon(name = "menu"),
                onPress = () => ref.asInstanceOf[DrawerLayout].openDrawer()
              )("React Native demo")
            )
          ),
          Route(RouteProps(path = RoutePath.HOME.path, render        = props => Home(redirPath, props.`match`), exact = true)),
          Route(RouteProps(path = RoutePath.ANTD.path, render        = props => Antd(redirPath, props.`match`))),
          Route(RouteProps(path = RoutePath.REACTROUTER.path, render = props => ReactRouter(redirPath, props.`match`)))
        )
      )
    )
  }
}

package hello.world

import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.Hooks._
import slinky.native._

import scala.scalajs.js.|

import typings.antdDashNative.AntdNativeFacade.{List => AntdList, _}
import typings.react.ScalableSlinky._
import typings.atAntDashDesignReactDashNative.PartialLocale
import typings.atBang88ReactDashNativeDashDrawerDashLayout.atBang88ReactDashNativeDashDrawerDashLayoutMod.{
  default,
  DrawerLayout
}
import typings.reactDashRouterDashNative.ReactRouterNativeFacade._

@react object App {

  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>
    var ref: default | Null = new default {}

    val (redirPath, updateRedirPath) = useState(RoutePath.HOME.path)

    val menus = RoutePath.allOrdered.indices.map(
      index =>
        ListItem(
          ListItemProps(
            onPress = _ => {
              updateRedirPath(RoutePath.allOrdered(index).path)
              ref.asInstanceOf[DrawerLayout].closeDrawer()
            }
          )
        )(
          RoutePath.allOrdered(index).title
        ).withKey(index.toString)
    )

    Provider(ProviderProps(locale = PartialLocale(locale = "enUS")))(
      Drawer(
        DrawerProps(
          drawerRef = (ref = _),
          sidebar   = ScrollView(WhiteSpace(WhiteSpaceProps(size = antdStrings.xl)), AntdList(ListProps())(menus)).toST
        )
      )(
        NativeRouter(NativeRouterProps())(
          View()(
            AntdList(ListProps(renderHeader = WhiteSpace(WhiteSpaceProps(size = antdStrings.xl)).toST))(
              ListItem(
                ListItemProps(
                  arrow   = antdStrings.horizontal,
                  onPress = _ => ref.asInstanceOf[DrawerLayout].openDrawer()
                )
              )("Open menu")
            )
          ),
          Route[Unit](path = RoutePath.HOME.path, render        = props => Home(redirPath, props.`match`), exact = true),
          Route[Unit](path = RoutePath.ANTD.path, render        = props => Antd(redirPath, props.`match`)),
          Route[Unit](path = RoutePath.REACTROUTER.path, render = props => ReactRouter(redirPath, props.`match`))
        )
      )
    )
  }
}

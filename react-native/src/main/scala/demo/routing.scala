package demo

import demo.Knowledge.Force
import org.scalablytyped.runtime.StringDictionary
import typingsSlinky.react.reactMod.{FC, ReactElement}
import typingsSlinky.reactDashNative.{reactDashNativeComponents => RN}
import typingsSlinky.reactDashNativeDashVectorDashIcons.{reactDashNativeDashVectorDashIconsComponents => Icons}
import typingsSlinky.reactDashNavigation._
import typingsSlinky.reactDashNavigation.reactDashNavigationMod._
import typingsSlinky.std.Object

object routing {
  import typingsSlinky.react.dsl._

  val DrawerContents: FC[DrawerItemsProps] = define.fc[DrawerItemsProps](
    p =>
      RN.View.props(
        RN.ViewProps(style = styles.wholeContainer),
        RN.View.props(
          RN.ViewProps(style = styles.drawerHeader),
          RN.View.props(
            RN.ViewProps(style = styles.drawerHeaderLogo),
            RN.Text.noprops("Scala.js")
          ),
          fromComponentType(DrawerItems).props(p)
        )
      )
  )

  def drawerRoute(label: String, muiIcon: String, component: NavigationComponent): NavigationRouteConfig =
    Object.assign(
      Anon_Screen(component),
      Anon_NavigationOptionsPath(
        NavigationDrawerScreenOptions(
          drawerLabel = label,
          drawerIcon = define
            .fc[Anon_Focused](
              props =>
                Icons.MaterialIcons.props(
                  Icons.IconProps(
                    name  = muiIcon,
                    size  = 24,
                    color = props.tintColor.force[String]
                  )
                )
            )
            .force[ReactElement]
        ): NavigationScreenConfig[NavigationDrawerScreenOptions]
      )
    )

  val DrawerNavigator: NavigationContainer =
    createDrawerNavigator(
      StringDictionary(
        "inbox" -> drawerRoute("Inbox", "inbox", screens.Inbox),
        "drafts" -> drawerRoute("Drafts", "drafts", screens.Drafts)
      ),
      DrawerNavigatorConfig(
        contentComponent = DrawerContents,
        contentOptions = Anon_ActiveBackgroundColor(
          activeTintColor = "black",
          style           = styles.drawerItems
        )
      )
    )

  val AppContainer: NavigationContainer =
    createAppContainer(DrawerNavigator.force)
}

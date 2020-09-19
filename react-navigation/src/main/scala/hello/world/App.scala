package hello.world

import slinky.core._
import slinky.core.annotations.react
import slinky.native.{Text, View}
import typings.reactNavigationCore.anon.InitialRouteName
import typings.reactNavigationStack.mod.createStackNavigator
import typings.reactNavigationCore.typesMod.TypedNavigator
@react object App {

  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>

    val stack: TypedNavigator[_, _, _, _, _] = createStackNavigator

    val initial: InitialRouteName[_] = InitialRouteName().setInitialRouteName("Home")

    val navigator: ReactComponentClass[_] = stack.Navigator

//    val screen = stack.Screen(_underscore = ???)

    View(Text("Whatever"))

  }

}

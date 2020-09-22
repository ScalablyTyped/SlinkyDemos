package hello.world

import hello.world.App.RoutePath
import slinky.core._
import slinky.core.annotations.react
import slinky.native.Text
import typings.antDesignReactNative.components.ListItem
import typings.reactRouter.mod._

@react object MenuList {

  case class Props(onClose: () => Unit)

  val component = FunctionalComponent[Props] { props =>

    val history = useHistory()

    RoutePath.allOrdered.zipWithIndex.map {
      case (route, index) => ListItem(Text(route.title))
        .onPress(_ => {
          props.onClose()
          history.push(route.path)
        })
        .withKey(index.toString)
    }
  }
}

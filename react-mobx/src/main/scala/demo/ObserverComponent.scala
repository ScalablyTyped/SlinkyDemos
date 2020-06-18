package slinky.core

import typings.mobxReact.mod.observer
import slinky.core.facade.ReactElement
import slinky.readwrite.Reader

import scala.scalajs.js

/* there isn't a place in the slinky API to apply decorators, so this hardcodes `@observer` for functional components */
object ObserverComponent {
  def apply[P](fn: P => ReactElement)(implicit name: FunctionalComponentName): FunctionalComponent[P] = {
    var ret: js.Function1[js.Object, ReactElement] = null
    ret = ((obj: js.Object) => {
      if (obj.hasOwnProperty("__")) {
        fn(obj.asInstanceOf[js.Dynamic].__.asInstanceOf[P])
      } else {
        fn(ret.asInstanceOf[js.Dynamic].__propsReader.asInstanceOf[Reader[P]].read(obj))
      }
    })

    if (!scala.scalajs.LinkingInfo.productionMode) {
      ret.asInstanceOf[js.Dynamic].displayName = name.name
    }

    val observerRet = observer(ret)

    new FunctionalComponent[P](observerRet)
  }
}

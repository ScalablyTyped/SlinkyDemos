package demo

import org.scalajs.dom.console
import slinky.core.annotations.react
import slinky.core.{FunctionalComponent, ObservingFC}
import slinky.web.html._
import typings.materialUi.{components => Mui}
import typings.mobx.computedvalueMod.IComputedValue
import typings.mobx.observablevalueMod.IObservableValue
import typings.mobx.{mod => MobX}

import scala.scalajs.js

@react
object MobXTest {
  case class Values(str: String, num: Double)

  case class Computed(strnum: String)

  class Store {
    val values: IObservableValue[Values] =
      MobX.observable.box(Values("hello", 42))

    val computed: IComputedValue[Computed] =
      MobX.computed(() => Computed(values.get().str + values.get().num))

    val increaseNum: js.Function1[Double, Unit] =
      MobX.action("increaseNum", (n: Double) => values.set(values.get().copy(num = values.get().num + n)))
  }

  case class Props(store: Store)

  val component: FunctionalComponent[Props] = ObservingFC[Props] {
    case Props(store) =>
      div(
        Mui.Avatar
          .onClick(e => console.warn(s"avatar clicked ${e.nativeEvent}"))
          .icon(
            div(
              onClick := (e => console.warn(s"icon clicked ${e.nativeEvent}"))
            )(":D")
          )
      )(
        "Current computed ",
        store.computed.get().strnum,
        " Current value num ",
        store.values.get().num,
        " Current value str ",
        store.values.get().str,
        " ",
        Mui.RaisedButton("increase num").onClick { e =>
          console.log("increase num")
          store.increaseNum(1)
        }
      )
  }
}

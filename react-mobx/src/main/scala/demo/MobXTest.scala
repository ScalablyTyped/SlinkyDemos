package demo

import org.scalajs.dom.console
import slinky.core.annotations.react
import slinky.core.{FunctionalComponent, ObservingFC}
import slinky.web.html._
import typingsSlinky.materialUi.{components => Mui}
import typingsSlinky.mobx.computedvalueMod.IComputedValue
import typingsSlinky.mobx.observablevalueMod.IObservableValue
import typingsSlinky.mobx.{mod => MobX}

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
      val increaseNum = () => {
        console.log("increase num")
        store.increaseNum(1)
      }

      div(
        Mui.Avatar(
          icon = div(
            onClick := (e => console.warn(s"icon clicked ${e.nativeEvent}"))
          )(":D")
        )(onClick := (e => console.warn(s"avatar clicked ${e.nativeEvent}")))
      )(
        "Current computed ",
        store.computed.get().strnum,
        " Current value num ",
        store.values.get().num,
        " Current value str ",
        store.values.get().str,
        " ",
        Mui.RaisedButton(onClick := increaseNum)("increase num")
      )
  }
}

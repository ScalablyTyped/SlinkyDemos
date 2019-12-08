package typingsSlinky.reactDashRedux

import slinky.core.{ExternalComponent, ReactComponentClass}
import typingsSlinky.reactDashRedux.reactDashReduxMod.connect
import typingsSlinky.redux.reduxMod.{Action, Dispatch, Store}

import scala.scalajs.js
import scala.scalajs.js.|

/**
  * This is very rudimentary, just enough to support the demo
  */
object ReduxFacade {

  /** Since redux forces us to use plain js objects,
    *  this is the only trivially extractable boilerplate
    */
  trait Extractor[T] {
    protected val _type: String
    def unapply(a: Action[String]): Option[T] =
      if (a.`type` == _type) Some(a.asInstanceOf[T]) else None
  }

  trait Connected[State, Action] extends js.Object {
    val dispatch: Dispatch[Action]
    val state:    State
  }

  /* take a store and a component which takes `dispatch` and `state` as props, return a component with those filled */
  def simpleConnect[State <: js.Any, Action <: js.Any, P <: js.Object](
      store: Store[State, Action],
      c:     ReactComponentClass[Connected[State, Action] with P]
  ): ExternalComponent { type Props = P } = {
    val keepState: js.Function1[State, js.Dynamic] =
      s => js.Dynamic.literal(state = s)
    val keepDispatch: js.Function1[Dispatch[Action], js.Dynamic] =
      d => js.Dynamic.literal(dispatch = d)

    new ExternalComponent() {
      override type Props = P
      override val component: String | js.Object =
        connect.asInstanceOf[js.Dynamic](keepState, keepDispatch)(c).asInstanceOf[js.Object]
    }
  }
}

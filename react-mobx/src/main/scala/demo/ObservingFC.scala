package slinky.core

import slinky.core.facade.ReactElement
import typings.mobxReact.mod.observer

object ObservingFC {
  def apply[P](fn: P => ReactElement)(implicit name: FunctionalComponentName): FunctionalComponent[P] = {
    val base = FunctionalComponent(fn)
    new FunctionalComponent(observer(base.component))
  }
}

package hello.world

import slinky.core.facade.ReactElement
import typingsSlinky.reactRouterNative.components.Redirect

trait Redirectable {

  def checkRedirection(redirPath: String, stayPath: String, elem: ReactElement): ReactElement =
    if (redirPath != stayPath)
      Redirect(to = redirPath)
    else
      elem

}

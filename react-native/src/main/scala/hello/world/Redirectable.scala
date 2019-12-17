package hello.world

import slinky.core.facade.ReactElement

import typings.reactDashRouterDashNative.ReactRouterNativeFacade.{Redirect, RedirectProps}

trait Redirectable {

  def checkRedirection(redirPath: String, stayPath: String, elem: ReactElement): ReactElement =
    if (redirPath != stayPath)
      Redirect(RedirectProps(to = redirPath))
    else
      elem

}

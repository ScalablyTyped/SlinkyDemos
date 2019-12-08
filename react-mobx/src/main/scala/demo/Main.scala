package demo

import slinky.web.ReactDOM
import typingsSlinky.std.window

object Main {
  def main(argv: Array[String]): Unit =
    ReactDOM.render(
      MainTabs(new MobXTest.Store, new GithubSearch.Store),
      window.document.getElementsByTagName("body")(0)
    )
}

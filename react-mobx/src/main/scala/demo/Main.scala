package demo

import org.scalajs.dom.document
import slinky.web.ReactDOM

object Main {
  def main(argv: Array[String]): Unit =
    ReactDOM.render(
      MainTabs(new MobXTest.Store, new GithubSearch.Store),
      document.getElementsByTagName("body")(0)
    )
}

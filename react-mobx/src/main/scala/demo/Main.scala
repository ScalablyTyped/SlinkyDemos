package demo

import org.scalajs.dom.document
import slinky.web.ReactDOM

object Main {
  def main(argv: Array[String]): Unit =
    ReactDOM.render(
      TodoList(new TodoStore, new PeopleStore),
      document.getElementsByTagName("body")(0)
    )
}
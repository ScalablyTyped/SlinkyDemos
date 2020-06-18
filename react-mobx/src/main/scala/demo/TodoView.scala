package demo

import org.scalajs.dom.window

import slinky.core._
import slinky.core.annotations.react
import slinky.web.html._

@react object TodoView {

  case class Props(todo: Todo, toggle: () => Unit, rename: String => Unit)

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] {
    case Props(todo, toggle, rename) =>
      val onRename = () =>
        window.prompt("Task name", todo.task) match {
          case e if e.isEmpty => ()
          case task           => rename(task)
        }

      val onToggleCompleted = () => {
        toggle()
      }

      li(onDoubleClick := onRename)(
        input(
          `type` := "checkbox",
          checked := todo.completed,
          onChange := onToggleCompleted
        ),
        todo.task,
        todo.assignee match {
          case Some(person) => small(person.name)
          case None         => span()
        }
      )
  }
}

package demo

import org.scalajs.dom.window

import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.Hooks._
import slinky.web.html._

@react object TodoList {

  case class Props(store: TodoStore, peopleStore: PeopleStore)

  val component: FunctionalComponent[Props] = ObserverComponent[Props] {
    case Props(store, peopleStore) =>
      useEffect(
        () => {
          store.assignTodo(0, Some(peopleStore.people.get()(0)))
          store.assignTodo(1, Some(peopleStore.people.get()(1)))
          peopleStore.renamePerson(0, "Michel Weststrate")
        },
        Seq() // run an effect and clean it up only once (on mount and unmount)
      )

      val onNewTodo = () =>
        window.prompt("Task name", "coffee plz") match {
          case e if e.isEmpty => ()
          case task           => store.addTodo(task)
        }

      val onLoadTodo = () => {
        store.increasePending()
        window.setTimeout(
          () => {
            store.addTodo("Random Todo " + Math.random())
            store.decreasePending()
          },
          2000
        )
        ()
      }

      val todoViews = {
        val ts = store.todos.get().todos
        ts.indices.map(index =>
          TodoView(
            ts(index),
            () => store.toggleTodo(index),
            (task: String) => store.renameTodo(index, task)
          ).withKey("td" + index)
        )
      }

      div(
        store.report.get(),
        ul(todoViews),
        ul(
          store.pendingRequests.get().c match {
            case 0 => div()
            case _ => li("Loading...")
          }
        ),
        button(onClick := onNewTodo)("New Todo"),
        small("double-click a todo to edit"),
        div(
          button(onClick := onLoadTodo)("Load async Todo")
        )
      )
  }
}

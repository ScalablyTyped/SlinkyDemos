package demo

import typings.mobx.computedvalueMod.IComputedValue
import typings.mobx.observablevalueMod.IObservableValue
import typings.mobx.{mod => MobX}

import scala.scalajs.js

case class Todo(task: String, completed: Boolean = false, assignee: Option[Person] = None) {

  def renameTask(t: String): Todo = Todo(t, completed, assignee)

  def toggleCompleted: Todo = Todo(task, !completed, assignee)

  def changeAssignee(a: Option[Person]): Todo = Todo(task, completed, a)

}

object Todo {

  def fromTask(task: String): Todo = Todo(task)

}

case class Todos(l: List[Todo]) {

  def completedCount(): Int = l.count(_.completed == true)

  def findUncompleted(): Option[Todo] = l.find(_.completed == false)

  def addNewTask(task: String): Todos = Todos(l :+ Todo.fromTask(task))

}

case class PendingRequests(c: Int) {

  def increase: PendingRequests = PendingRequests(c + 1)

  def decrease: PendingRequests = PendingRequests(c - 1)

}

class TodoStore {

  val todos: IObservableValue[Todos] =
    MobX.observable.box(Todos(List(Todo("read MobX tutorial"), Todo("try MobX"))))

  val pendingRequests: IObservableValue[PendingRequests] =
    MobX.observable.box(PendingRequests(0))

  val report: IComputedValue[String] =
    MobX.computed { () =>
      val ts = todos.get()
      "Next todo: " + (ts.findUncompleted() match {
        case Some(nextTodo) => nextTodo.task
        case None           => "<none>"
      }) + ". Progress: " + ts.completedCount() + "/" + ts.l.length
    }

  def changeTodos(f: Todos => Todos): Unit = todos.set(f(todos.get()))

  def updateTodo(index: Int, f: Todo => Todo): Unit =
    changeTodos(t => Todos(t.l.updated(index, f(todos.get().l(index)))))

  val addTodo: js.Function1[String, Unit] =
    MobX.action("addTodo", (task: String) => changeTodos(_.addNewTask(task)))

  val toggleTodo: js.Function1[Int, Unit] =
    MobX.action("toggleTodo", (index: Int) => updateTodo(index, _.toggleCompleted))

  val renameTodo: js.Function2[Int, String, Unit] =
    MobX.action("renameTodo", (index: Int, task: String) => updateTodo(index, _.renameTask(task)))

  val assignTodo: js.Function2[Int, Option[Person], Unit] =
    MobX.action("assignTodo", (index: Int, assignee: Option[Person]) => updateTodo(index, _.changeAssignee(assignee)))

  def changeRequests(f: PendingRequests => PendingRequests): Unit = pendingRequests.set(f(pendingRequests.get()))

  val increasePending: js.Function0[Unit] =
    MobX.action("increasePending", () => changeRequests(_.increase))

  val decreasePending: js.Function0[Unit] =
    MobX.action("decreasePending", () => changeRequests(_.decrease))
}

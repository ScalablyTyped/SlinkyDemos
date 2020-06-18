package demo

import typings.mobx.observablevalueMod.IObservableValue
import typings.mobx.{mod => MobX}

import scala.scalajs.js

case class Person(name: String) {
  def changeName(n: String): Person = Person(n)
}

class PeopleStore {

  val people: IObservableValue[List[Person]] =
    MobX.observable.box(List(Person("Michel"), Person("Me")))

  def changePeople(f: List[Person] => List[Person]): Unit = people.set(f(people.get()))

  def updatePerson(index: Int, f: Person => Person): Unit = changePeople(_.updated(index, f(people.get()(index))))

  val renamePerson: js.Function2[Int, String, Unit] =
    MobX.action("renamePerson", (index: Int, name: String) => updatePerson(index, _.changeName(name)))

}

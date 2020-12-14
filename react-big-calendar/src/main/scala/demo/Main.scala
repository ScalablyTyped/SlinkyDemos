package demo

import org.scalajs.dom.document
import slinky.web.ReactDOM
import typings.moment.{mod => Moment}
import typings.moment.momentStrings
import typings.reactBigCalendar.mod.{momentLocalizer, View}
import typings.reactBigCalendar.components.Calendar

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("react-big-calendar/lib/css/react-big-calendar.css", JSImport.Namespace)
@js.native
object BigCalendarCss extends js.Object

class Event(val start: js.Date, val end: js.Date, val title: js.UndefOr[String]) extends js.Object

object Main {

  BigCalendarCss // touch to load css

  val Localizer = momentLocalizer(Moment.^)

  val someEvent = new Event(
    start = new js.Date,
    end = Moment.apply(new js.Date).add(1, momentStrings.day).toDate(),
    title = "My amazing event"
  )

  def main(argv: Array[String]): Unit = {
    val container = document.getElementById("container")
    ReactDOM.render(
      Calendar[Event, js.Object](Localizer)
        .eventsVarargs(someEvent)
        .defaultDate(new js.Date)
        .defaultView(View.week)
        .viewsVarargs(View.agenda, View.day, View.week),
      container
    )
  }
}

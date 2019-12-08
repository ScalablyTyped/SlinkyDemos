package demo

import slinky.web.ReactDOM
import typingsSlinky.moment.momentMod.{^ => Moment}
import typingsSlinky.moment.momentStrings
import typingsSlinky.reactDashBigDashCalendar.reactDashBigDashCalendarMod.{View, momentLocalizer}
import typingsSlinky.reactDashBigDashCalendar.{components => BC}
import typingsSlinky.std.{console, document}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

@JSImport("react-big-calendar/lib/css/react-big-calendar.css", JSImport.Namespace)
@js.native
object BigCalendarCss extends js.Object

class Event(val start: js.Date, val end: js.Date, val title: js.UndefOr[String]) extends js.Object

object Main {

  BigCalendarCss // touch to load css

  val Localizer = momentLocalizer(Moment)

  val someEvent = new Event(
    start = new js.Date,
    end   = Moment(new js.Date).add(1, momentStrings.day).toDate(),
    title = "My amazing event"
  )

  def main(argv: Array[String]): Unit =
    Knowledge.asOption(document.getElementById("container")) match {
      case Some(container) =>
        ReactDOM.render(
          BC.Calendar[Event, js.Object](
            localizer   = Localizer,
            events      = js.Array(someEvent),
            defaultDate = new js.Date,
            defaultView = View.week,
            views       = js.Array(View.agenda, View.day, View.week)
          ),
          container
        )
      case None => console.error("Could not find #container")
    }
}

object Knowledge {
  def asOption[T](t: T | Null): Option[T] =
    Option(t.asInstanceOf[T])
}

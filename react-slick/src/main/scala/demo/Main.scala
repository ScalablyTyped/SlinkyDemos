package demo

import org.scalajs.dom.document
import slinky.core.FunctionalComponent
import slinky.core.facade.{Hooks, ReactElement}
import slinky.web.ReactDOM
import slinky.web.html._
import typings.reactSlick.components.ReactSlick

import scala.scalajs.js

object Main {

  case class Props(images: js.Array[String])
  case class State(selectedIdx: Option[Int])

  val SlickTest =
    FunctionalComponent[Props] { props =>
      val (state, setState) = Hooks.useState(State(None))

      def myOnClick(idx: Int): () => Unit = () => {
        println(s"clicked image $idx")
        setState(State(Some(idx)))
      }

      val images: js.Array[ReactElement] =
        props.images.zipWithIndex.map {
          case (source, idx) =>
            img(key := idx.toString, src := source, onClick := myOnClick(idx))
        }

      div(
        style := js.Dynamic.literal(position = "relative", left = "200px", width = 500, height = 500),
        label(
          style := js.Dynamic.literal(color = "blue"),
          s"Selected image index: ${state.selectedIdx.fold("none")(_.toString)}"
        ),
        ReactSlick(
          onInit = () => println("slick init"),
          dots = true,
          autoplay = true,
          autoplaySpeed = 1000,
          slidesToShow = 2
        )(images.to(Seq): _*)
      )
    }

  def main(argv: Array[String]): Unit =
    ReactDOM.render(
      SlickTest(
        Props(
          js.Array(
            "https://img.buzzfeed.com/buzzfeed-static/static/2014-04/enhanced/webdr07/4/16/original-16439-1396642689-17.jpg?downsize=800:*&output-format=auto&output-quality=auto",
            "https://img.buzzfeed.com/buzzfeed-static/static/2014-04/enhanced/webdr03/4/16/enhanced-26552-1396642701-1.jpg?downsize=800:*&output-format=auto&output-quality=auto",
            "https://img.buzzfeed.com/buzzfeed-static/static/2014-04/enhanced/webdr07/4/16/enhanced-16354-1396642706-25.jpg?downsize=800:*&output-format=auto&output-quality=auto",
            "https://img.buzzfeed.com/buzzfeed-static/static/2014-04/enhanced/webdr04/10/12/enhanced-buzz-29081-1397145781-14.jpg?downsize=800:*&output-format=auto&output-quality=auto",
            "https://img.buzzfeed.com/buzzfeed-static/static/2014-04/enhanced/webdr06/4/16/enhanced-11136-1396643149-13.jpg?downsize=800:*&output-format=auto&output-quality=auto",
            "https://img.buzzfeed.com/buzzfeed-static/static/2014-04/enhanced/webdr02/9/12/enhanced-buzz-11844-1397060009-22.jpg?downsize=800:*&output-format=auto&output-quality=auto",
            "https://img.buzzfeed.com/buzzfeed-static/static/2014-04/enhanced/webdr07/9/12/enhanced-buzz-28527-1397060122-10.jpg?downsize=800:*&output-format=auto&output-quality=auto"
          )
        )
      ),
      document.body
    )
}

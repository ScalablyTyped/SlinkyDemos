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
        ReactSlick
          .onInit(() => println("slick init"))
          .dots(true)
          .autoplay(true)
          .autoplaySpeed(1000)
          .slidesToShow(2)(images.to(Seq): _*)
      )
    }

  def main(argv: Array[String]): Unit =
    ReactDOM.render(
      SlickTest(
        Props(
          js.Array(
            "https://i.pinimg.com/474x/a8/30/69/a8306979f24cbf615e1cc0a635ceb384.jpg",
            "https://i.pinimg.com/474x/b0/15/4c/b0154cfc2fe3a664ac8f679df4debf56.jpg",
            "https://i.imgur.com/FqeTKrS.jpg",
            "https://static.boredpanda.com/blog/wp-content/uploads/2019/11/cat-fluffy-squirrel-tail-bell-7-5dca63b7b11a8__700.jpg",
            "https://i.chzbgr.com/full/9428254976/hD3DA6B8F/cat",
          )
        )
      ),
      document.body
    )
}

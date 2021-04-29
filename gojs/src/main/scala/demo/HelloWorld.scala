package demo

import org.scalablytyped.runtime.StringDictionary
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import typings.StBuildingComponent.make
import typings.gojs.mod.{Diagram, GraphLinksModel}
import typings.gojsReact.components.ReactDiagram

import scala.scalajs.js

@react
object HelloWorld {

  type Props = Unit

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { _ =>
    def nodes =
      js.Array(
        StringDictionary[js.Any]("key" -> "Hello", "key" -> "World!")
      )

    def links =
      js.Array(
        StringDictionary[js.Any]("from" -> "Hello", "to" -> "World!")
      )

    def diagram = {
      val diagram = new Diagram()
      val model = new GraphLinksModel()
      model.linkKeyProperty = "key"
      diagram.model = model
      diagram
    }

    make(
      ReactDiagram(
        "HelloWorldDiv",
        () => diagram,
        nodes,
        skipsDiagramUpdate = false
      )
        .linkDataArray(links)
        .onModelChange(_ => ())
    )

  }

}

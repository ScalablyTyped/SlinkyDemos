package demo

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import typings.gojs.{mod => go}
import typings.gojsReact.components.ReactDiagram

import scala.scalajs.js

@react
object HelloWorld {

  type Props = Unit

  // javascript is interesting. I think this just works in typescript because the compiler just gives up on checking anything in the presence of `any`
  def tweakedParse: js.Function2[ /* val */ Any, /* targetObj */ Any, Any] =
    (`val`, targetObj) => go.Point.parse(`val`.asInstanceOf[String])
  def tweakedStringify: js.Function3[ /* val */ Any, /* srcData */ Any, /* model */ Any, Any] =
    (`val`, srcData, model) => go.Point.stringify(`val`.asInstanceOf[go.Point])

  class ArchetypeData(val text: js.UndefOr[String], val color: js.UndefOr[String]) extends go.ObjectData
  class Data(val key: String, val text: String, val color: String, val loc: String) extends go.ObjectData
  class LinkData(val key: Int, val from: Int, val to: Int) extends go.ObjectData

  def nodes: js.Array[go.ObjectData] =
    js.Array(
      new Data("0", "Alpha", "lightblue", "0 0"),
      new Data("1", "Beta!", "orange", "150 0"),
      new Data("2", "Gamma!", "lightgreen", "0 150"),
      new Data("3", "Delta!", "pink", "150 150")
    )

  def links: js.Array[go.ObjectData] =
    js.Array(
      new LinkData(-1, 0, 1),
      new LinkData(-2, 0, 2),
      new LinkData(-3, 1, 1),
      new LinkData(-4, 2, 3),
      new LinkData(-5, 3, 0)
    )

  def diagram(): go.Diagram = {
    val diagram = new go.Diagram()
    diagram.undoManager.isEnabled = true // must be set to allow for model change listening
    diagram.toolManager.clickCreatingTool.archetypeNodeData = new ArchetypeData("new node", "lightblue")
    diagram.model = {
      val model = new go.GraphLinksModel()
      model.linkKeyProperty = "key"
      model
    }
    diagram.nodeTemplate = {
      val node = new go.Node(go.Panel.Auto)
      node.bind {
        val binding = new go.Binding("location", "loc", tweakedParse)
        binding.makeTwoWay(tweakedStringify)
      }
      node.add {
        val shape = new go.Shape()
        shape.figure = "RoundedRectangle"
        shape.name = "SHAPE"
        shape.fill = "white"
        shape.strokeWidth = 0
        shape.bind(new go.Binding("fill", "color"))
        shape
      }
      node.add {
        val block = new go.TextBlock()
        block.margin = 8
        block.editable = true
        block.bind(new go.Binding("text").makeTwoWay())
        block
      }
      node
    }
    diagram
  }

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { _ =>
    ReactDiagram(
      divClassName = "diagram-component",
      initDiagram = diagram,
      nodeDataArray = nodes,
      skipsDiagramUpdate = false
    )
      .linkDataArray(links)
      .onModelChange(_ => ())
  }
}

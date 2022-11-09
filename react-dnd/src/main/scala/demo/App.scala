package demo

import slinky.core._
import slinky.core.annotations.react
import slinky.web.html._
import typings.csstype.mod.{ClearProperty, FloatProperty, TextAlignProperty}
import typings.dndCore.libInterfacesMod.SourceType
import typings.react.mod.CSSProperties
import typings.reactDnd.components.DndProvider
import typings.reactDnd.libInterfacesHooksApiMod.{DragSourceHookSpec, DropTargetHookSpec}
import typings.reactDnd.mod.{useDrag, useDrop}
import typings.reactDndHtml5Backend.mod.HTML5Backend
import typings.std.global.alert

import scala.language.implicitConversions
import scala.scalajs.js

object ItemTypes {
  val BOX = "box"
}

@react
object Dustbin {
  type Props = Unit

  case class Collected(isOver: Boolean, canDrop: Boolean)

  class DropResult(val name: String) extends js.Object

  val styles = CSSProperties()
    .setHeight("12rem")
    .setWidth("12rem")
    .setMarginRight("1.5rem")
    .setMarginBottom("1.5rem")
    .setColor("white")
    .setPadding("1rem")
    .setTextAlign(TextAlignProperty.center)
    .setFontSize("1rem")
    .setLineHeight("normal")
    .setFloat(FloatProperty.left)

  val component = FunctionalComponent[Props] {
    case () =>
      val js.Tuple2(Collected(canDrop, isOver), drop) =
        useDrop(
          DropTargetHookSpec[js.Object, DropResult, Collected](ItemTypes.BOX)
            .setDrop((_, _) => new DropResult("Dustbin"))
            .setCollect(monitor => Collected(monitor.isOver, monitor.canDrop))
        )

      val isActive = canDrop && isOver

      val backgroundColor: String =
        if (isActive) "darkgreen"
        else if (canDrop) "darkkhaki"
        else "#222"

      div(
        ref := (elem => drop(elem, js.undefined): Unit), // can also just cast `drop` to `ReactRef`
        style := styles.duplicate.setBackgroundColor(backgroundColor),
        if (isActive) "Release to drop" else "Drag a box here"
      )
  }
}

@react
object Box {
  case class Props(name: String)

  class Dragged(val name: String, val `type`: SourceType) extends js.Object

  val styles = CSSProperties()
    .setBorder("1px dashed gray")
    .setBackgroundColor("white")
    .setPadding("0.5rem 1rem")
    .setMarginRight("1.5rem")
    .setMarginBottom("1.5rem")
    .setCursor("move")
    .setFloat(FloatProperty.left)

  val component = FunctionalComponent[Props] {
    case Props(name) =>
      val js.Tuple3(isDragging, drag, _) =
        useDrag(
          DragSourceHookSpec[Dragged, Dustbin.DropResult, Boolean](item = new Dragged(name, ItemTypes.BOX))
            .setEnd { (itemU, monitor) =>
              itemU.foreach { item =>
                val dropResult = monitor.getDropResult()
                alert(s"You dropped ${item.name} into ${dropResult.asInstanceOf[Dustbin.DropResult].name}!")
              }
            }
            .setCollect(monitor => monitor.isDragging)
        )

      val opacity = if (isDragging) "0.4" else "1"

      div(ref := (elem => drag(elem, js.undefined): Unit), style := styles.duplicate.setOpacity(opacity), name)
  }
}

@react object Container {
  type Props = Unit

  val component = FunctionalComponent[Unit] {
    case () =>
      div(
        div(style := CSSProperties().setOverflow("hidden").setClear(ClearProperty.both), Dustbin()),
        div(
          style := CSSProperties().setOverflow("hidden").setClear(ClearProperty.both),
          Box("Glass"),
          Box("Banana"),
          Box("Paper")
        )
      )
  }
}

@react object App {
  type Props = Unit

  val component = FunctionalComponent[Props] {
    case () => div(className := "App")(DndProvider.Backend(HTML5Backend)(Container()))
  }
}

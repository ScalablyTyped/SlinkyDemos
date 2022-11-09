package demo

import slinky.core._
import slinky.core.annotations.react
import typings.leaflet.mod.LatLngExpression
import typings.reactLeaflet.components.{Map, Marker, Popup, TileLayer}
import typings.reactLeaflet.mod.{MapProps, MarkerProps, PopupProps, TileLayerProps}

import scala.language.implicitConversions
import scala.scalajs.js

@react object App {
  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>
    val position: LatLngExpression = js.Tuple2(51.505, -0.09)

    Map[MapProps, js.Any](MapProps(children = null).setCenter(position).setZoom(13))(
      TileLayer[TileLayerProps, js.Any](
        TileLayerProps(url = "https://tile.openstreetmap.org/{z}/{x}/{y}.png")
          .setAttribution("&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors")
      ),
      Marker[MarkerProps, js.Any](MarkerProps(position = position))(
        Popup[PopupProps, js.Any](PopupProps(children = null))("A pretty CSS3 popup.\nEasily customizable.")
      )
    )
  }
}

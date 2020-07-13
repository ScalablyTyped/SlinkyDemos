package demo

import slinky.core._
import slinky.core.annotations.react
import typings.leaflet.mod.{LatLngExpression, Map_, Marker_, Popup_, TileLayer_}
import typings.reactLeaflet.components.{Map, Marker, Popup, TileLayer}
import typings.reactLeaflet.mod.{MapProps, MarkerProps, PopupProps, TileLayerProps}

import scala.language.implicitConversions
import scala.scalajs.js

@react object App {
  type Props = Unit

  val component = FunctionalComponent[Props] { _ =>
    val position: LatLngExpression = js.Tuple2(51.505, -0.09)

    Map[MapProps, Map_](MapProps().setCenter(position).setZoom(13))(
      TileLayer[TileLayerProps, TileLayer_](
        TileLayerProps(url = "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png")
          .setAttribution("&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors")
      ),
      Marker[MarkerProps, Marker_[js.Any]](MarkerProps(position = position))(
        Popup[PopupProps, Popup_](PopupProps())("A pretty CSS3 popup.\nEasily customizable.")
      )
    )
  }
}

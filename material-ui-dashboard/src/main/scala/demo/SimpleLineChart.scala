package demo

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import typings.recharts.components._
import typings.recharts.rechartsStrings.monotone

import scala.scalajs.js

// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/SimpleLineChart.js
@react object SimpleLineChart {

  // TODO maybe there is another way to create this
  val data: js.Array[js.Object] = scala.scalajs.js.Array(
    new js.Object {
      val Name   = "Mon"
      val Visits = 200
      val Orders = 3400
    },
    new js.Object {
      val Name   = "Tue"
      val Visits = 128
      val Orders = 2398
    },
    new js.Object {
      val Name   = "Wed"
      val Visits = 5000
      val Orders = 4300
    },
    new js.Object {
      val Name   = "Thu"
      val Visits = 4780
      val Orders = 2908
    },
    new js.Object {
      val Name   = "Fri"
      val Visits = 5890
      val Orders = 4800
    },
    new js.Object {
      val Name   = "Sat"
      val Visits = 4390
      val Orders = 3800
    },
    new js.Object {
      val Name   = "Sun"
      val Visits = 4490
      val Orders = 4300
    }
  )

  val component: FunctionalComponent[Unit] = FunctionalComponent[Unit] {
    case () =>
      ResponsiveContainer
        .width("99%")
        .height(320)(
          LineChart.data(data)(
            XAxis.dataKey("Name"),
            YAxis(),
            CartesianGrid.vertical(false).strokeDasharray("3 3"),
            Tooltip(),
            Legend(),
            Line("Visits").`type`(monotone).stroke("#82ca9d"),
            Line("Orders").`type`(monotone).stroke("#8884d8")
              .activeDot(true) // TODO the original is activeDot={{ r: 8 }}
          )
        )
  }

}


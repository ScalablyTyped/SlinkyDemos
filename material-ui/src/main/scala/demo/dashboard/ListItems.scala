package demo.dashboard

import slinky.web.html._
import typings.materialUiCore.{components => Mui}
import typings.materialUiIcons.components.{Dashboard, ShoppingCart, People, BarChart, Layers, Assignment}

// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/listItems.js
object ListItems {

  val mainListItems = {
    div()(
      Mui.ListItem.button(true)(
        Mui.ListItemIcon()(
          Dashboard()
        ),
        Mui.ListItemText.primary("Dashboard")
      ),
      Mui.ListItem.button(true)(
        Mui.ListItemIcon()(
          ShoppingCart()
        ),
        Mui.ListItemText.primary("Orders")
      ),
      Mui.ListItem.button(true)(
        Mui.ListItemIcon()(
          People()
        ),
        Mui.ListItemText.primary("Customers")
      ),
      Mui.ListItem.button(true)(
        Mui.ListItemIcon()(
          BarChart()
        ),
        Mui.ListItemText.primary("Reports")
      ),
      Mui.ListItem.button(true)(
        Mui.ListItemIcon()(
          Layers()
        ),
        Mui.ListItemText.primary("Integrations")
      ),
    )
  }

  val secondaryListItems = {
    div()(
      Mui.ListSubheader.inset(true)(""),
      Mui.ListItem.button(true)(
        Mui.ListItemIcon()(
          Assignment()
        ),
        Mui.ListItemText.primary("Current month")
      ),
      Mui.ListItem.button(true)(
        Mui.ListItemIcon()(
          Assignment()
        ),
        Mui.ListItemText.primary("Last quarter")
      ),
      Mui.ListItem.button(true)(
        Mui.ListItemIcon()(
          Assignment()
        ),
        Mui.ListItemText.primary("Year-end sale")
      )
    )
  }

}

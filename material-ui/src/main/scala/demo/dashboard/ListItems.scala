package demo.dashboard

import slinky.core.facade.ReactElement
import slinky.web.html._
import typings.materialUiCore.components.{ListItem, ListItemIcon, ListItemText, ListSubheader}
import typings.materialUiIcons.{components => Icon}

// https://github.com/mui-org/material-ui/blob/v3.x/docs/src/pages/getting-started/page-layout-examples/dashboard/listItems.js
object ListItems {

  val mainListItems: ReactElement =
    div(
      ListItem.button(true)(
        ListItemIcon(Icon.Dashboard()),
        ListItemText.primary("Dashboard")
      ),
      ListItem.button(true)(
        ListItemIcon(Icon.ShoppingCart()),
        ListItemText.primary("Orders")
      ),
      ListItem.button(true)(
        ListItemIcon(Icon.People()),
        ListItemText.primary("Customers")
      ),
      ListItem.button(true)(
        ListItemIcon(Icon.BarChart()),
        ListItemText.primary("Reports")
      ),
      ListItem.button(true)(
        ListItemIcon(Icon.Layers()),
        ListItemText.primary("Integrations")
      )
    )

  val secondaryListItems: ReactElement =
    div(
      ListSubheader.inset(true)("Saved reports"),
      ListItem.button(true)(
        ListItemIcon(Icon.Assignment()),
        ListItemText.primary("Current month")
      ),
      ListItem.button(true)(
        ListItemIcon(Icon.Assignment()),
        ListItemText.primary("Last quarter")
      ),
      ListItem.button(true)(
        ListItemIcon(Icon.Assignment()),
        ListItemText.primary("Year-end sale")
      )
    )
}

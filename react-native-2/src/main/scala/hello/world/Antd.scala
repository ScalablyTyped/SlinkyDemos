package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks.useState
import slinky.native.{ScrollView, Text, View}

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.Dynamic.literal

import typings.antdDashNative.AntdNativeFacade.{Button, _}
import typings.react.ScalableSlinky._
import typings.atAntDashDesignIconsDashReactDashNative.libFillMod.IconFillProps
import typings.atAntDashDesignIconsDashReactDashNative.libOutlineMod.IconOutlineProps
import typings.atAntDashDesignReactDashNative.PartialLocale
import typings.atAntDashDesignReactDashNative.libModalPropsTypeMod.Action

@react object Antd {

  case class Props(redirPath: String)

  val component = FunctionalComponent[Props] { props =>
    val (isModalVisible, updateIsModalVisible) = useState(false)

    View(
      Redir(props.redirPath, RoutePath.ANTD.path),
      Text(
        style = Styles.title
      )("Antd components"),
      ScrollView(
        automaticallyAdjustContentInsets = false,
        showsHorizontalScrollIndicator   = false,
        showsVerticalScrollIndicator     = false
      )(
        List(ListProps(renderHeader = Text()("List header").toST))(
          ListItem(ListItemProps(arrow = antdStrings.horizontal, onPress = _ => updateIsModalVisible(true)))(
            "Open modal"
          ),
          ListItem(ListItemProps(arrow = antdStrings.horizontal, onPress = _ => antdToast.success("Successful!")))(
            "Launch success toast"
          )
        ),
        View(
          style = literal(
            backgroundColor = "white",
            flex            = 1,
            flexDirection   = "column",
            justifyContent  = "center",
            alignItems      = "center"
          )
        )(
          InputItem(InputItemProps(placeholder = "input text")),
          InputItem(
            InputItemProps(
              `type`       = antdStrings.password,
              placeholder  = "password",
              error        = true,
              onErrorClick = _ => antdToast.fail("Always wrong!"),
              last         = true
            )
          ),
          WingBlank(WingBlankProps(size = antdStrings.lg))(
            Button(
              ButtonProps(
                onPress = _ => antdToast.fail("Failure!"),
                `type`  = antdStrings.primary
              )
            )("Launch fail toast")
          ),
          WhiteSpace(WhiteSpaceProps(size = antdStrings.xl)),
          IconFill(IconFillProps(name       = "flag", size = 20))(),
          IconOutline(IconOutlineProps(name = "gift"))(),
          Icon(IconProps(name               = "experiment", size = antdStrings.lg, color = "#A82"))()
        )
      ),
      Modal(
        ModalProps(
          visible      = isModalVisible,
          transparent  = true,
          maskClosable = true,
          closable     = false,
          title        = "Basic modal",
          onClose      = () => updateIsModalVisible(false),
          footer = js.Array(
            Action("Cancel", () => updateIsModalVisible(false), ""),
            Action("OK", () => updateIsModalVisible(false), "")
          )
        )
      )(Text()("Some contents..."))
    )
  }
}

package hello.world

import org.scalablytyped.runtime.StringDictionary
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.core.facade.Hooks.useState
import slinky.native.ScrollView

import scala.scalajs.js
import typingsSlinky.antDesignReactNative.components.{List => AntdList, _}
import typingsSlinky.antDesignReactNative.{antDesignReactNativeStrings => antdStrings}
import typingsSlinky.antDesignReactNative.mod.Toast
import typingsSlinky.antDesignIconsReactNative.components.{IconFill, IconOutline}
import typingsSlinky.antDesignReactNative.modalPropsTypeMod.Action
import typingsSlinky.reactNative.mod.ViewStyle
import typingsSlinky.reactRouter.mod.`match`
import typingsSlinky.reactNative.reactNativeStrings

@react object Antd extends Redirectable {

  case class Props(redirPath: String, `match`: `match`[_])

  val component = FunctionalComponent[Props] { props =>
    val (isModalVisible, updateIsModalVisible) = useState(false)

    checkRedirection(
      props.redirPath,
      props.`match`.path,
      View(
        Text(style = Styles.title)("Antd components"): ReactElement,
        ScrollView(
          automaticallyAdjustContentInsets = false,
          showsHorizontalScrollIndicator = false,
          showsVerticalScrollIndicator = false
        )(
          AntdList(renderHeader = Text("List header"): ReactElement)(
            ListItem(arrow = antdStrings.horizontal, onPress = () => updateIsModalVisible(true))(
              "Open modal"
            ),
            ListItem(arrow = antdStrings.horizontal, onPress = () => Toast.success("Successful!"))(
              "Launch success toast"
            )
          ),
          View(
            style = ViewStyle(
              backgroundColor = "white",
              flex = 1,
              flexDirection = reactNativeStrings.column,
              justifyContent = reactNativeStrings.center,
              alignItems = reactNativeStrings.center
            )
          )(
            InputItem(placeholder = "input text"),
            InputItem(
              placeholder = "password",
              `type` = antdStrings.password,
              error = true,
              onErrorClick = _ => { Toast.fail("Always wrong!") },
              last = true
            ),
            WingBlank(size = antdStrings.lg)(
              Button(onPress = _ => Toast.fail("Failure!"), `type` = antdStrings.primary)("Launch fail toast")
            ),
            WhiteSpace(size = antdStrings.xl),
            IconFill(name = "flag", _overrides = StringDictionary("size" -> 40)),
            IconOutline(name = "gift", _overrides = StringDictionary("size" -> 80)),
            Icon(name = "experiment", size = antdStrings.lg, color = "#A82")
          )
        ),
        Modal(
          visible = isModalVisible,
          transparent = true,
          maskClosable = true,
          closable = false,
          title = "Basic modal",
          onClose = () => updateIsModalVisible(false),
          footer = js.Array(
            Action("Cancel", () => updateIsModalVisible(false), ""),
            Action("OK", () => updateIsModalVisible(false), "")
          )
        )(Text("Some contents..."))
      )
    )
  }
}

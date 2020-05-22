package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks.useState
import slinky.core.facade.ReactElement
import slinky.native.ScrollView
import typings.antDesignIconsReactNative.antDesignIconsReactNativeStrings.{flag, gift}
import typings.antDesignIconsReactNative.components.{IconFill, IconOutline}
import typings.antDesignReactNative.components.{List => AntdList, _}
import typings.antDesignReactNative.mod.Toast
import typings.antDesignReactNative.modalPropsTypeMod.Action
import typings.antDesignReactNative.{antDesignReactNativeStrings => antdStrings}
import typings.reactNative.mod.{FlexAlignType, ViewStyle}
import typings.reactNative.reactNativeStrings

import scala.scalajs.js

@react object Antd {

  type Props = Unit

  val component = FunctionalComponent[Props] {
    case () =>
      val (isModalVisible, updateIsModalVisible) = useState(false)

      View(
        Text.style(Styles.title)("Antd components"),
        ScrollView(
          automaticallyAdjustContentInsets = false,
          showsHorizontalScrollIndicator = false,
          showsVerticalScrollIndicator = false
        )(
          AntdList.renderHeader(Text("List header"): ReactElement)(
            ListItem
              .arrow(antdStrings.horizontal)
              .onPress(e => updateIsModalVisible(true))("Open modal"),
            ListItem
              .arrow(antdStrings.horizontal)
              .onPress(e => Toast.success("Successful!"))(
                "Launch success toast"
              )
          ),
          View.style(
            ViewStyle(
              backgroundColor = "white",
              flex = 1,
              flexDirection = reactNativeStrings.column,
              justifyContent = reactNativeStrings.center,
              alignItems = FlexAlignType.center
            )
          )(
            InputItem.placeholder("input text"),
            InputItem
              .placeholder("password")
              .`type`(antdStrings.password)
              .error(true)
              .onErrorClick(_ => Toast.fail("Always wrong!"))
              .last(true),
            WingBlank.size(antdStrings.lg)(
              Button
                .onPress(_ => Toast.fail("Failure!"))
                .`type`(antdStrings.primary)("Launch fail toast")
            ),
            WhiteSpace.size(antdStrings.xl),
            IconFill(flag).size(40),
            IconOutline(name = gift).size(80),
            Icon(name = "experiment").size(antdStrings.lg).color("#A82")
          )
        ),
        Modal(visible = isModalVisible)
          .transparent(true)
          .maskClosable(true)
          .closable(false)
          .title("Basic modal")
          .onClose(() => updateIsModalVisible(false))
          .footer(
            js.Array(
              Action("Cancel", () => updateIsModalVisible(false)),
              Action("OK", () => updateIsModalVisible(false))
            )
          )
      )(Text("Some contents..."))
  }
}

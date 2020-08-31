package hello.world

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import slinky.core.facade.Hooks.useState
import slinky.native.ScrollView
import typings.antDesignIconsReactNative.antDesignIconsReactNativeStrings.{flag, gift}
import typings.antDesignIconsReactNative.components.{IconFill, IconOutline}
import typings.antDesignReactNative.components.{List => AntdList, _}
import typings.antDesignReactNative.mod.Toast
import typings.antDesignReactNative.modalPropsTypeMod.Action
import typings.antDesignReactNative.{antDesignReactNativeStrings => antdStrings}
import typings.reactNative.mod.{FlexAlignType, ViewStyle}
import typings.reactNative.reactNativeStrings

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
          AntdList.renderHeader(Text("List header").build)(
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
            ViewStyle()
              .setBackgroundColor("white")
              .setFlex(1)
              .setFlexDirection(reactNativeStrings.column)
              .setJustifyContent(reactNativeStrings.center)
              .setAlignItems(FlexAlignType.center)
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
          .footerVarargs(
            Action("Cancel").setOnPress(() => updateIsModalVisible(false)),
            Action("OK").setOnPress(() => updateIsModalVisible(false))
          )
      )(Text("Some contents..."))
  }
}

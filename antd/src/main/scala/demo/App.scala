package demo

import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.{Hooks, ReactElement}
import slinky.web.html._
import typings.antDesignIcons.{components => Icons}
import typings.antd.antdStrings
import typings.antd.components._
import typings.antd.notificationMod.{ArgsProps, IconType, default => Notification}
import typings.antd.tableInterfaceMod.ColumnType
import typings.std.global.console

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("antd/dist/antd.css", JSImport.Default)
@js.native
object CSS extends js.Any

@react object App {
  type Props = Unit

  private val css = CSS

  val component = FunctionalComponent[Props] { _ =>
    val (isModalVisible, updateIsModalVisible) = Hooks.useState(false)
    val (selectValue, updateSelectValue) = Hooks.useState("lucy")

    val renderIntro = Row(
      Col.span(7),
      Col.span(10)(
        header(className := "App-header")(h1(className := "App-title")("Welcome to React (with Scala.js!)")),
        p(className := "App-intro")("To get started, edit ", code("App.scala"), " and save to reload.")
      ),
      Col.span(7)
    )

    def renderGrid =
      section(
        h2("Grid"),
        Row(
          Col.span(12)(div(className := "block blue1")("col-12")),
          Col.span(12)(div(className := "block blue2")("col-12"))
        ),
        Row(
          Col.span(8)(div(className := "block blue1")("col-8")),
          Col.span(8)(div(className := "block blue2")("col-8")),
          Col.span(8)(div(className := "block blue1")("col-8"))
        ),
        Row(
          Col.span(6)(div(className := "block blue1")("col-6")),
          Col.span(6)(div(className := "block blue2")("col-6")),
          Col.span(6)(div(className := "block blue1")("col-6")),
          Col.span(6)(div(className := "block blue2")("col-6"))
        )
      )

    def renderTag =
      section(
        h2("Tag"),
        Tag("Tag 1"),
        Tag.color(antdStrings.red)("red")
      )

    class TableItem(val key: Int, val name: String, val age: Int, val address: String) extends js.Object

    def renderTable =
      section(
        h2("Table"),
        Table[TableItem]
          .dataSource(
            js.Array(
              new TableItem(1, "Mike", 32, "10 Downing St."),
              new TableItem(2, "John", 42, "10 Downing St.")
            )
          )
          .columns(
            js.Array(
              ColumnType[TableItem]()
                .setTitleReactElement("Name")
                .setDataIndex("name")
                .setKey("name")
                .setRender((_, tableItem, _) => Tag(tableItem.name): ReactElement),
              ColumnType[TableItem].setTitleReactElement("Age").setDataIndex("age").setKey("age"),
              ColumnType[TableItem].setTitleReactElement("Address").setDataIndex("address").setKey("address")
            )
          )
      )

    val renderAlert = section(
      h2("Alert"),
      Alert
        .message("Success Tips")
        .description("Detailed description and advice about successful copywriting.")
        .`type`(antdStrings.success)
        .showIcon(true)
    )

    val renderButton =
      section(
        h2("Button"),
        Button.icon(Icons.DownloadOutlined()).`type`(antdStrings.primary)("Download")
      )

    val renderModal = section(
      h2("Modal"),
      Button.onClick(_ => updateIsModalVisible(true))("Open modal"),
      Modal
        .visible(isModalVisible)
        .title("Basic modal")
        .onCancel(_ => updateIsModalVisible(false))
        .onOk(_ => updateIsModalVisible(false))(
          p("Some contents..."),
          p("Some contents..."),
          p("Some contents...")
        )
    )

    val renderSelect = section(
      h2("Select"),
      Select[String]
        .defaultValue(selectValue)
        .onChange((changedValue, _) => updateSelectValue(changedValue))(
          Option("jack")("Jack"),
          Option("lucy")("Lucy"),
          Option("disabled")("Disabled").disabled(true),
          Option("yiminghe")("Yiminghe")
        )
    )

    val renderIcon = section(h2("Icon"), Icons.HomeOutlined())

    val renderInput = section(
      h2("Input"),
      Input
        .addonBefore(Icons.UserOutlined())
        .placeholder("Basic usage")
        .onChange(event => console.log(event.target_ChangeEvent.value))
    )

    val renderPassword =
      section(h2("Password Input"), Password.addonBefore("Password").placeholder("input password"))

    val renderSpin = section(
      h2("Spin"),
      Spin
        .size(antdStrings.large)
        .spinning(true)(
          Alert
            .message("Alert message title")
            .description("Further details about the context of this alert.")
            .`type`(antdStrings.info)
            .showIcon(true)
        )
    )

    val renderForm = section(
      h2("Form"),
      Form.onFinish { store =>
        console.log("Form submitted", store)
      }(
        FormItem(
          Input.addonBefore(Icons.MailTwoTone()).`type`(antdStrings.email).placeholder("input email")
        ),
        FormItem(
          Password.addonBefore(Icons.LockTwoTone()).`type`(antdStrings.password).placeholder("input password")
        ),
        FormItem(Button.htmlType(antdStrings.submit).`type`(antdStrings.primary))("Log in")
      )
    )

    val renderCoordinated =
      section(h2("Form coordinated controls"), CoordinatedDemo("write note here"))

    val renderNotification = section(
      h2("Notification"),
      Button.onClick(_ =>
        Notification.open(
          ArgsProps()
            .setMessage("Notification Title")
            .setDescription(
              "This is the content of the notification. This is the content of the notification. This is the content of the notification."
            )
            .setType(IconType.success)
        )
      )("Show notification")
    )

    div(className := "App")(
      renderIntro,
      Row(
        Col.span(2),
        Col.span(20)(
          renderGrid,
          renderTag,
          renderTable,
          renderAlert,
          renderButton,
          renderModal,
          renderSelect,
          renderIcon,
          renderInput,
          renderPassword,
          renderSpin,
          renderForm,
          renderCoordinated,
          renderNotification
        ),
        Col.span(2)
      )
    )
  }
}

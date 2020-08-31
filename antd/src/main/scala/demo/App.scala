package demo

import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.Hooks._
import slinky.core.facade.{Hooks, ReactElement}
import slinky.web.html._
import typings.antDesignIcons.components.AntdIcon
import typings.antDesignIconsSvg.downOutlinedMod.{default => DownOutlinedIcon}
import typings.antDesignIconsSvg.downloadOutlinedMod.{default => DownloadOutlinedIcon}
import typings.antDesignIconsSvg.homeOutlinedMod.{default => HomeOutlinedIcon}
import typings.antDesignIconsSvg.lockTwoToneMod.{default => LockTwoToneIcon}
import typings.antDesignIconsSvg.mailTwoToneMod.{default => MailTwoToneIcon}
import typings.antDesignIconsSvg.shopOutlinedMod.{default => ShopOutlinedIcon}
import typings.antDesignIconsSvg.userOutlinedMod.{default => UserOutlinedIcon}
import typings.antd.antdStrings
import typings.antd.components.{List => AntList, _}
import typings.antd.notificationMod.{ArgsProps, IconType, default => Notification}
import typings.antd.tableInterfaceMod.{ColumnGroupType, ColumnType}
import typings.rcSelect.interfaceMod.OptionData
import typings.react.mod.CSSProperties
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
    val (multiSelectValue, updateMultiSelectValue) = Hooks.useState(List("a10", "c12"))

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
        Tag.color(antdStrings.red)("red"),
        Tag.CheckableTag(true)("Checkable")
      )

    class TableItem(val key: Int, val name: String, val age: Int, val address: String) extends js.Object

    def renderTable =
      section(
        h2("Table"),
        Table[TableItem]
          .bordered(true)
          .dataSourceVarargs(
            new TableItem(1, "Mike", 32, "10 Downing St."),
            new TableItem(2, "John", 42, "10 Downing St.")
          )
          .columnsVarargs(
            ColumnType[TableItem]()
              .setTitleReactElement("Name")
              .setDataIndex("name")
              .setKey("name")
              .setRender((_, tableItem, _) => Tag(tableItem.name).build),
            ColumnGroupType[TableItem](
              scala.scalajs.js.Array(
                ColumnType[TableItem].setTitle("Age").setDataIndex("age").setKey("age"),
                ColumnType[TableItem].setTitle("Address").setDataIndex("address").setKey("address")
              )
            ).setTitleReactElement("Age & Address")
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
        Button.icon(AntdIcon(DownloadOutlinedIcon)).`type`(antdStrings.primary)("Download")
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
          Select.Option("jack")("Jack"),
          Select.Option("lucy")("Lucy"),
          Select.Option("disabled")("Disabled").disabled(true),
          Select.Option("yiminghe")("Yiminghe")
        )
    )

    val renderMultiSelect = section(
      h2("Multiple select"),
      Select[js.Array[String]]
        .defaultValue(js.Array(multiSelectValue: _*))
        .mode(antdStrings.multiple)
        .onChange((changedValue, _) => updateMultiSelectValue(changedValue.toList))(
          (10 until 36).toList.map { n =>
            val s = s"${(n + 87).toChar}${n.toString}"
            Select.Option(s)(s).withKey(s)
          }
        )
    )

    val renderGroupSelect = section(
      h2("Select with grouped options"),
      Select[String]
        .defaultValue(selectValue)
        .onChange((changedValue, _) => updateSelectValue(changedValue))(
          Select.OptGroup.label("Manager")(
            Select.Option("jack")("Jack"),
            Select.Option("lucy")("Lucy")
          ),
          Select.OptGroup.label("Engineer")(
            Select.Option("yiminghe")("Yiminghe")
          )
        )
    )

    val renderIcon = section(h2("Icon"), AntdIcon(HomeOutlinedIcon))

    val renderInput = section(
      h2("Input"),
      Input
        .addonBefore(AntdIcon(UserOutlinedIcon))
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
          Input.addonBefore(AntdIcon(MailTwoToneIcon)).`type`(antdStrings.email).placeholder("input email")
        ),
        FormItem(
          Password.addonBefore(AntdIcon(LockTwoToneIcon)).`type`(antdStrings.password).placeholder("input password")
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

    def menu: ReactElement =
      Menu.onClick(mi =>
        Notification.open(
          ArgsProps()
            .setMessage("Selected menu item")
            .setDescription(
              s"Menu Item with key '${mi.key}' was selected"
            )
            .setType(IconType.success)
        )
      )(
        MenuItem.withKey("1")("Option 1"),
        MenuItem.withKey("2")("Option 2"),
        MenuItem.withKey("3")("Option 3")
      )

    def renderDropdown: ReactElement =
      section(
        h2("Dropdown with Menu"),
        Dropdown(menu).className("spaced")(
          Button("Dropdown Button", AntdIcon(DownOutlinedIcon))
        ),
        Dropdown(menu)
          .triggerVarargs(antdStrings.click)
          .className("spaced")(
            Button("Dropdown Button, responds to click", AntdIcon(DownOutlinedIcon))
          )
      )

    def renderMenu: ReactElement =
      section(
        h2("Menu"),
        menu
      )

    val autoCompleteComponent: FunctionalComponent[Unit] = FunctionalComponent[Unit] { _ =>
      val (text, setText) = useState("")
      AutoComplete
        .style(CSSProperties().setWidth("100%"))
        .value(text)
        .filterOption(true) // Filter options by input
        .defaultActiveFirstOption(true) // Make first option active - enter to select
        .options(
          js.Array(
            OptionData("Alphabet"),
            OptionData("Baguette").set(
              "label",
              span(AntdIcon(ShopOutlinedIcon), " Baguette")
            ), // Set label as a ReactElement for customised display
            OptionData("Bicycle"),
            OptionData("Croissant")
          )
        )
        .onChange { case (text, _) => setText(text) }
    }

    def renderAutocomplete: ReactElement =
      section(
        h2("Autocomplete"),
        autoCompleteComponent(())
      )

    def renderFooter: ReactElement =
      div(style := js.Dynamic.literal(height = "100px"))

    val renderAvatar = section(
      h2("Avatar"),
      Avatar.size(antdStrings.large).icon(AntdIcon(UserOutlinedIcon))
    )

    val renderBadge = section(
      h2("Badge"),
      Badge.count(5)(Button("badge"))
    )

    val renderComment = section(
      h2("Comment"),
      Comment
        .author("Author")
        .avatar(Avatar.size(antdStrings.large).icon(AntdIcon(UserOutlinedIcon)))
        .content("Comment")
        .actionsVarargs(Button("Like"))
    )

    val renderCollapse = section(
      h2("Collapse"),
      Collapse(
        Collapse.Panel.header("Panel1")("Collapsable Content"),
        Collapse.Panel.header("Panel2")("Collapsable Content"),
        Collapse.Panel.header("Panel3")("Collapsable Content")
      )
    )

    val renderCarousel = section(
      h2("Carousel"),
      Carousel(
        h3(1),
        h3(2),
        h3(3)
      )
    )

    val renderCard = section(
      h2("Card"),
      Space(
        Card
          .title("Card With Meta")
          .cover(
            img(
              src := "https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png",
              style := CSSProperties().setMaxWidth("200px")
            )
          )(
            Card.Meta.title("Meta Title").description("Meta Description")
          ),
        Card.title("Card With Grid")(
          Card.Grid("Content"),
          Card.Grid("Content"),
          Card.Grid("Content")
        )
      )
    )

    val renderCalendar = section(
      h2("Calendar"),
      Calendar()
    )

    val renderDescriptions = section(
      h2("Descriptions"),
      Descriptions
        .title("Descriptions")
        .bordered(true)(
          Descriptions.Item.label("Product")("Cloud Database"),
          Descriptions.Item.label("Billing Mode")("Prepaid")
        )
    )

    val renderEmpty = section(
      h2("Empty"),
      Empty()
    )

    val renderList = section(
      h2("List"), {
        def item: ReactElement =
          AntList.Item(
            AntList.Item.Meta
              .avatar(Avatar.icon(AntdIcon(UserOutlinedIcon)))
              .title("Title")
              .description("Description")
          )

        AntList()(Seq.fill(3)(item): _*)
      }
    )

    val renderPopover = section(
      h2("Popover"),
      Popover
        .titleReactElement("Title")
        .contentReactElement("Content")(
          Button("Hover Me")
        )
    )

    val renderStatistic = section(
      h2("Statistic"),
      Statistic.title("Statistic").value(12.345).precision(2),
      Countdown.title("Countdown").value(456)
    )

    val renderTooltip = section(
      h2("Tooltip"),
      Tooltip.TooltipPropsWithOverlayRefAttributes.titleReactElement("Tooltip")(span("Hover me"))
    )

    val renderTimeline = section(
      h2("Timeline"),
      Timeline(
        Timeline.Item("Item 1"),
        Timeline.Item("Item 2"),
        Timeline.Item("Item 3")
      )
    )

    val renderTabs = section(
      h2("Tabs"),
      Tabs(
        Tabs.TabPane.tab("Tab 1").withKey("tab1")("Content 1"),
        Tabs.TabPane.tab("Tab 2").withKey("tab2")("Content 2"),
        Tabs.TabPane.tab("Tab 3").withKey("tab3")("Content 3")
      )
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
          renderMultiSelect,
          renderGroupSelect,
          renderIcon,
          renderInput,
          renderPassword,
          renderSpin,
          renderForm,
          renderCoordinated,
          renderNotification,
          renderDropdown,
          renderMenu,
          renderAutocomplete,
          renderAvatar,
          renderBadge,
          renderComment,
          renderCollapse,
          renderCarousel,
          renderCard,
          renderCalendar,
          renderDescriptions,
          renderEmpty,
          renderList,
          renderPopover,
          renderStatistic,
          renderTooltip,
          renderTimeline,
          renderTabs
        ),
        renderFooter,
        Col.span(2)
      )
    )
  }
}

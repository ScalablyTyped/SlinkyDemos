package demo

import slinky.core._
import slinky.core.annotations.react
import slinky.core.facade.Hooks._
import slinky.core.facade.ReactElement
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
import typings.antd.components.Form.{Form => FormItem}
import typings.antd.generatePickerMod.RangePickerDateProps
import typings.antd.mod
import typings.antd.notificationMod.{ArgsProps, IconType, default => Notification}
import typings.antd.tableInterfaceMod.{ColumnGroupType, ColumnType}
import typings.antd.tooltipMod.{TooltipProps, TooltipPropsWithTitle}
import typings.moment.mod.Moment
import typings.moment.mod.unitOfTime.DurationConstructor
import typings.moment.{mod => moment}
import typings.rcPicker.interfaceMod.{EventValue, RangeValue}
import typings.rcPicker.rangePickerMod.RangeShowTimeObject
import typings.rcSelect.selectMod
import typings.rcTreeSelect.{strategyUtilMod, treeSelectMod}
import typings.react.mod.{CSSProperties, RefAttributes}
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
    val (isModalVisible, updateIsModalVisible) = useState(false)
    val (selectValue, updateSelectValue) = useState("lucy")
    val (selectTreeValues, updateSelectTreeValues) = useState(js.Array("0-0"))
    val (rangePickerValues, updateRangePickerValues) = useState[RangeValue[Moment]] {
      val endMoment = moment.apply()
      val startMoment = endMoment.subtract(DurationConstructor.hours, 2)
      // need type descriptions to help Scala infer that there can be `| Null` at two levels here
      js.Tuple2(startMoment: EventValue[Moment], endMoment: EventValue[Moment])
    }
    val (multiSelectValue, updateMultiSelectValue) = useState(List("a10", "c12"))

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
              .setTitle("Name": ReactElement)
              .setDataIndex("name")
              .setKey("name")
              .setRender((_, tableItem, _) => Tag(tableItem.name).build),
            ColumnGroupType[TableItem](
              scala.scalajs.js.Array(
                ColumnType[TableItem].setTitle("Age": ReactElement).setDataIndex("age").setKey("age"),
                ColumnType[TableItem].setTitle("Address": ReactElement).setDataIndex("address").setKey("address")
              )
            ).setTitleReactElement("Age & Address")
          )
      )

    val renderAlert = section(
      h2("Alert"),
      Alert("Success Tips")
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
          Select.OptGroup("Manager")(
            Select.Option("jack")("Jack"),
            Select.Option("lucy")("Lucy")
          ),
          Select.OptGroup("Engineer")(
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
      Form[Seq[_]].onFinish { store =>
        console.log("Form submitted", store)
      }(
        FormItem()(
          Input.addonBefore(AntdIcon(MailTwoToneIcon)).`type`(antdStrings.email).placeholder("input email")
        ),
        FormItem()(
          Password.addonBefore(AntdIcon(LockTwoToneIcon)).`type`(antdStrings.password).placeholder("input password")
        ),
        FormItem()(Button.htmlType(antdStrings.submit).`type`(antdStrings.primary))("Log in")
      )
    )

    val renderCoordinated =
      section(h2("Form coordinated controls"), CoordinatedDemo("write note here"))

    val renderNotification = section(
      h2("Notification"),
      Button.onClick(_ =>
        Notification.open(
          ArgsProps(message = "Notification Title")
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
          ArgsProps(message = "Selected menu item")
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
        .optionsVarargs(
          selectMod.DefaultOptionType("Alphabet"),
          selectMod.DefaultOptionType("Baguette").set(
            "label",
            span(AntdIcon(ShopOutlinedIcon), " Baguette")
          ), // Set label as a ReactElement for customised display
          selectMod.DefaultOptionType("Bicycle"),
          selectMod.DefaultOptionType("Croissant")
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
      Comment("Comment")
        .author("Author")
        .avatar(Avatar.size(antdStrings.large).icon(AntdIcon(UserOutlinedIcon)))
        .actionsVarargs(Button("Like"))
    )

    val renderCollapse = section(
      h2("Collapse"),
      Collapse(
        Collapse.Panel(header = "Panel1")("Collapsable Content"),
        Collapse.Panel(header = "Panel2")("Collapsable Content"),
        Collapse.Panel(header = "Panel3")("Collapsable Content")
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
      Tooltip(TooltipPropsWithTitle("Tooltip": ReactElement).asInstanceOf[TooltipProps with RefAttributes[Any]])(span("Hover me"))
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

    val renderRangePicker = section(
      h2("Range Picker"),
      mod.DatePicker.RangePicker.create(
        RangePickerDateProps[Moment]()
        .setShowTime(RangeShowTimeObject[Moment].setFormat("HH:mm"))
        .setFormat("YYYY/MM/DD HH:mm")
        .setValue(rangePickerValues)
        .setOnChange { (values: RangeValue[Moment], formatString: js.Tuple2[String, String]) =>
          console.log(formatString)
          updateRangePickerValues(values)
        }
      ),
      div(b("Note that moment.js date times are ready to be localized")),
      div(s"moment.js current locale: ${moment.apply().locale()}"),
      div(s"moment.js duration en_US: ${moment.apply().locale("en_US").fromNow()}"),
      div(s"moment.js duration es_ES: ${moment.apply().locale("es_ES").fromNow()}"),
      div(s"moment.js duration fr_FR: ${moment.apply().locale("fr_FR").fromNow()}"),
      div(s"moment.js duration it_IT: ${moment.apply().locale("it_IT").fromNow()}")
    )

    val renderTreeSelect = section(
      h2("Multiple and checkable Tree Select"), {
        def node(title: String, value: String) =
          treeSelectMod.DefaultOptionType().setTitle(title).setValue(value).setKey(value)

        val data: js.Array[treeSelectMod.DefaultOptionType] = js.Array(
          node("Node1", "0-0").setChildrenVarargs(
            node("Child Node1", "0-0-0")
          ),
          node("Node2", "0-1").setChildrenVarargs(
            node("Child Node3", "0-1-0"),
            node("Child Node4", "0-1-1"),
            node("Child Node5", "0-1-2")
          )
        )

        TreeSelect[js.Array[String], treeSelectMod.DefaultOptionType]()
          .value(selectTreeValues)
          .onChange((values, _, _) => updateSelectTreeValues(values))
          .treeData(data)
          .placeholder("Please select")
          .treeCheckable(true)
          .showCheckedStrategy(strategyUtilMod.SHOW_PARENT)
          .style(CSSProperties().setWidth("100%"))
      }
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
          renderTreeSelect,
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
          renderRangePicker,
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

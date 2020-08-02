package demo

import org.scalablytyped.runtime.StringDictionary
import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import typings.antd.antdStrings
import typings.antd.components._
import typings.antd.formFormMod.useForm
import typings.antd.gridColMod.ColProps
import typings.rcFieldForm.interfaceMod.BaseRule
import typings.std.global.console

@react
object CoordinatedDemo {
  case class Props(noteTitle: String)

  val component = FunctionalComponent[Props] { props =>
    val form = useForm().head
    Form
      .form(form)
      .labelCol(ColProps().setSpan(5))
      .wrapperCol(ColProps().setSpan(12))
      .onFinish(store => console.log("Received values of form: ", store))(
        FormItem
          .label(props.noteTitle)
          .name("note")
          .rulesVarargs(BaseRule().setRequired(true).setMessage("Please input your note!"))(
            Input()
          ),
        FormItem
          .label("Gender")
          .name("gender")
          .rulesVarargs(BaseRule().setRequired(true).setMessage("Please select your gender!'"))(
            Select[String]
              .placeholder("Select a option and change input text above")
              .onChange { (value, _) =>
                form.setFieldsValue(
                  StringDictionary(
                    "gender" -> value,
                    "note" -> s"Hi, ${if (value == "male") "man" else "lady"}!"
                  )
                )
              }(
                Select.Option(value = "male")("Male"),
                Select.Option(value = "female")("Female")
              )
          ),
        FormItem.wrapperCol(ColProps().setSpan(12).setOffset(5))(
          Button.`type`(antdStrings.primary).htmlType(antdStrings.submit)("Submit")
        )
      )
  }
}

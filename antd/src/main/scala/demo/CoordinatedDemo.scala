package demo

import slinky.core.FunctionalComponent
import slinky.core.annotations.react
import typings.antd.antdStrings
import typings.antd.components.Form.{Form => FormItem}
import typings.antd.components._
import typings.antd.libFormFormMod.useForm
import typings.antd.libGridColMod.ColProps
import typings.antd.libFormHooksUseFormMod.FormInstance
import typings.rcFieldForm.esInterfaceMod.{AggregationRule, FieldData}
import typings.std.global.console

import scala.scalajs.js

@react
object CoordinatedDemo {
  case class Props(noteTitle: String)

  class Values(val gender: String, val note: String) extends js.Object

  val component = FunctionalComponent[Props] { props =>
    val form: FormInstance[Values] = useForm[Values]().head
    Form[Values]()
      .form(form)
      .labelCol(ColProps().setSpan(5))
      .wrapperCol(ColProps().setSpan(12))
      .onFinish(store => console.log(s"Received values of form. gender: ${store.gender}, note: ${store.note}", store))(
        FormItem[Values]()
          .label(props.noteTitle)
          .name("note")
          .rulesVarargs(AggregationRule().setRequired(true).setMessage("Please input your note!"))(
            Input()
          ),
        FormItem[Values]()
          .label("Gender")
          .name("gender")
          .rulesVarargs(AggregationRule().setRequired(true).setMessage("Please select your gender!'"))(
            Select[String]
              .placeholder("Select a option and change input text above")
              .onChange { (value, _) =>
                form.setFields(
                  js.Array(
                    FieldData("gender").setValue(value),
                    FieldData("note").setValue(s"Hi, ${if (value == "male") "man" else "lady"}!")
                  )
                )
              }(
                Select.Option(value = "male")("Male"),
                Select.Option(value = "female")("Female")
              )
          ),
        FormItem[Values]().wrapperCol(ColProps().setSpan(12).setOffset(5))(
          Button.`type`(antdStrings.primary).htmlType(antdStrings.submit)("Submit")
        )
      )
  }
}

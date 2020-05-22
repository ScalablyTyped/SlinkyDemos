package demo

import org.scalajs.dom.console
import slinky.core.ExternalComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import typings.antd.antdStrings
import typings.antd.components._
import typings.antd.formFormMod.{Form => _, _}
import typings.antd.gridColMod.ColProps

import scala.scalajs.js
import scala.scalajs.js.{|, JSON}

@react
object CoordinatedDemo {

  // case class won't work because `Form.create` will rewrite the props object
  class Props(val noteTitle: String) extends js.Object

  object Facade {

    /**
      * This is an example of something a bit more complicated than just rewriting component types, and which a manually
      *  written facade. Given an implementation of a component which has a `form` prop which is to be prefilled,
      *  this will generate a ready-to-use `ExternalComponent` for it.
      */
    def formComponent[P <: js.Object](options: FormCreateOption[P])(f: js.Function1[P with WithForm, ReactElement]): ExternalComponent { type Props = P } =
      new ExternalComponent {
        override type Props = P
        override val component: String | js.Object =
          typings.antd.formFormMod.default.create(options)(f).asInstanceOf[js.Object]
      }

    trait WithForm extends js.Object {
      val form: WrappedFormUtils[js.Object]
    }
  }

  val component: ExternalComponent { type Props = CoordinatedDemo.Props } =
    Facade.formComponent(FormCreateOption[Props](name = "coordinated")) { props =>
      val noteInput = {
        val options = GetFieldDecoratorOptions(
          rules = js.Array(ValidationRule(required = true, message = "Please input your note!"))
        )

        props.form.getFieldDecorator("note", options).apply(Input())
      }

      val genderInput = {
        val select = Select[String]()
          .placeholder("Select a option and change input text above")
          .onChange { (value, _) =>
            console.log(value)
            props.form.setFieldsValue(new js.Object {
              val note: String = "Hi, " + {
                if (value == "male") "man" else "lady"
              } + "!"
            })
          }(
            Option.value("male")("male"),
            Option.value("female")("female")
          )
        val options = GetFieldDecoratorOptions(
          rules = js.Array(ValidationRule(required = true, message = "Please select your gender!'"))
        )
        props.form.getFieldDecorator("gender", options).apply(select)
      }

      Form
        .labelCol(ColProps(span = 5))
        .wrapperCol(ColProps(span = 12))
        .onSubmit { e =>
          e.preventDefault()
          val cb: ValidateCallback[js.Object] = (err, values) =>
            if (err == null) {
              console.log("Received values of form: " + JSON.stringify(values))
            }
          props.form.validateFields(cb)
        }(
          FormItem.label(props.noteTitle)(
            noteInput
          ),
          FormItem.label("Gender")(
            genderInput
          ),
          FormItem.wrapperCol(ColProps(span = 12, offset = 5))(
            Button.`type`(antdStrings.primary).htmlType(antdStrings.submit)("Submit")
          )
        )
    }
}

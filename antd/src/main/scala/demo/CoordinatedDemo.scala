package demo

import org.scalajs.dom.{console, Event}
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.core.{ExternalComponent, SyntheticEvent, TagMod}
import slinky.web.html._
import typings.antd.antdStrings
import typings.antd.components._
import typings.antd.formFormMod.{FormCreateOption, GetFieldDecoratorOptions, ValidationRule, WrappedFormUtils}
import typings.antd.gridColMod.ColProps

import scala.scalajs.js
import scala.scalajs.js.{|, JSON}

@react
object CoordinatedDemo {

  // case class won't work because `Form.create` will rewrite the props object
  class Props(val noteTitle: String) extends js.Object

  object Facade {

    import typings.antd.formFormMod.default.{create => createForm}

    /**
      * This is an example of something a bit more complicated than just rewriting component types, and which a manually
      *  written facade. Given an implementation of a component which has a `form` prop which is to be prefilled,
      *  this will generate a ready-to-use `ExternalComponent` for it.
      */
    def formComponent[P <: js.Object](options: FormCreateOption[P])(f: js.Function1[P with WithForm, ReactElement]) =
      new ExternalComponent {
        override type Props = P
        override val component: String | js.Object =
          createForm(options)(f).asInstanceOf[js.Object]
      }

    trait WithForm extends js.Object {
      val form: WrappedFormUtils[js.Object]
    }

    def decoratedField(form: WrappedFormUtils[js.Object], fieldName: String, options: GetFieldDecoratorOptions)(
        children: ReactElement
    ): TagMod[Any] = form.getFieldDecorator(fieldName, options).apply(children)
  }

  val component: ExternalComponent { type Props = CoordinatedDemo.Props } =
    Facade.formComponent(FormCreateOption[Props](name = "coordinated")) { props =>
      val handleSubmit: SyntheticEvent[form.tag.RefType, Event] => Unit = e => {
        e.preventDefault()
        props.form.validateFields { (err, values) =>
          if (err == null) {
            console.log("Received values of form: " + JSON.stringify(values))
          }
        }
      }

      def handleSelectChange(value: String, option: Any): Unit = {
        console.log(value)
        props.form.setFieldsValue(new js.Object {
          val note = "Hi, " + { if (value == "male") "man" else "lady" } + "!"
        })
      }

      val noteInput = {
        val options = GetFieldDecoratorOptions(
          rules = js.Array(ValidationRule(required = true, message = "Please input your note!"))
        )
        Facade.decoratedField(props.form, "note", options) { Input() }
      }

      val genderInput = {
        val options = GetFieldDecoratorOptions(
          rules = js.Array(ValidationRule(required = true, message = "Please select your gender!'"))
        )
        Facade.decoratedField(props.form, "gender", options) {
          Select[String](
            placeholder = "Select a option and change input text above",
            onChange = handleSelectChange
          )(
            Option()(value := "male")("male"),
            Option()(value := "female")("female")
          )
        }
      }

      Form(labelCol = ColProps(span = 5), wrapperCol = ColProps(span = 12))(onSubmit := handleSubmit)(
        FormItem(label = props.noteTitle)(noteInput),
        FormItem(label = "Gender")(genderInput),
        FormItem(wrapperCol = ColProps(span = 12, offset = 5))(
          Button(`type` = antdStrings.primary, htmlType = antdStrings.submit)("Submit")
        )
      )
    }
}

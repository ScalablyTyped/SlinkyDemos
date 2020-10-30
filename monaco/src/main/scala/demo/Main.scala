package demo

import org.scalajs.dom.document
import slinky.core.FunctionalComponent
import slinky.core.facade.Hooks
import slinky.web.ReactDOM
import slinky.web.html._
import typings.monacoEditor.mod.Environment
import typings.monacoEditor.monacoEditorRequire
import typings.reactMonacoEditor.components.ReactMonacoEditor
import typings.std.global.console

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

object Main {

  val code =
    """

// Define Typescript Interface Employee
interface Employee {
  firstName: String;
  lastName: String;
  contractor?: Boolean;
}

  // Use Typescript Interface Employee. 
  // This should show you an error on john 
  // as required attribute lastName is missing
  const john:Employee = {
  firstName:"John",
  // lastName:"Smith"
  // contractor:true
}

"""
  val SimpleTypescriptEditor = FunctionalComponent[String] {
    case code =>
      val (state, setState) = Hooks.useState(code)

      ReactMonacoEditor
        .editorDidMount { (editor, monaco) =>
          // alert user that they typed `a`
          editor.onKeyUp {
            case keyEvent if keyEvent.code == "KeyA" => console.warn("You typed a")
            case _                                   => ()
          }

          // configure typescript to be strict
          monaco.languages.typescript.typescriptDefaults.getCompilerOptions.setStrict(true)
        }
        .width("600")
        .height("800")
        .language("typescript")
        .theme("vs-dark")
        .defaultValue("")
        .value(state)
        .onChange((newValue, _) => setState(newValue))
  }

  // not sure why we had to name the global this to be able to set `MonacoEnvironment`
  @JSGlobal("self")
  @js.native
  object self extends js.Object {
    var MonacoEnvironment: js.UndefOr[Environment] = js.native
  }

  def main(argv: Array[String]): Unit = {
    /* touch to load first*/
    monacoEditorRequire

    /* connect language to web worker bundle (see custom webpack setup) */
    self.MonacoEnvironment = Environment().setGetWorkerUrl {
      case (_, "typescript" | "javascript") => "./ts.worker-bundle.js"
      case (_, "json")                      => "./json.worker-bundle.js"
      case (_, "css")                       => "./css.worker-bundle.js"
      case (_, "html")                      => "./html.worker-bundle.js"
      case _                                => "./editor.worker-bundle.js"
    }

    ReactDOM.render(div(SimpleTypescriptEditor(code)), document.body)
  }
}

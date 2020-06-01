package demo.dashboard

import org.scalablytyped.runtime.StringDictionary
import typings.materialUiStyles.makeStylesMod.StylesHook
import typings.materialUiStyles.mod.makeStyles
import typings.materialUiStyles.withStylesMod.{CSSProperties, StyleRules, StyleRulesCallback, WithStylesOptions}

import scala.scalajs.js

object StyleBuilder {
  @inline def apply[Theme, Props <: js.Object]: StyleBuilder[Theme, Props] =
    new StyleBuilder[Theme, Props](_ => StringDictionary.empty)
}

@inline final class StyleBuilder[T, P] private (val f: StyleRulesCallback[T, P, String]) extends AnyVal {
  @inline def add(key: String, value: CSSProperties): StyleBuilder[T, P] =
    new StyleBuilder[T, P]({ theme =>
      val ret = f(theme)
      ret.update(key, value)
      ret
    })

  @inline def add(key: String, withTheme: T => CSSProperties): StyleBuilder[T, P] =
    new StyleBuilder[T, P]({ theme =>
      val ret = this.f(theme)
      ret.update(key, withTheme(theme))
      ret
    })

  @inline def add(key: String, withThemeProps: (T, P) => CSSProperties): StyleBuilder[T, P] =
    new StyleBuilder[T, P]({ theme =>
      val ret : StyleRules[P, String] = this.f(theme)
      val x: js.Function1[P, CSSProperties] = (props: P) => withThemeProps(theme, props)
      ret.update(key, x)
      ret
    })

  @inline def make: StylesHook[StyleRulesCallback[T, P, String]] =
    make(WithStylesOptions())

  @inline def make(opts: WithStylesOptions): StylesHook[StyleRulesCallback[T, P, String]] =
    makeStyles[StyleRulesCallback[T, P, String]](f, opts)
}


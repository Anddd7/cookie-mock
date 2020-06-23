package com.github.anddd7.cookiemock.util

import com.github.anddd7.cookiemock.domain.Condition
import com.github.anddd7.cookiemock.domain.Cookie
import com.github.anddd7.cookiemock.domain.Target
import org.springframework.web.reactive.function.server.RequestPredicate
import org.springframework.web.reactive.function.server.RequestPredicates.method
import org.springframework.web.reactive.function.server.RequestPredicates.path
import javax.script.ScriptEngineManager

object RequestPredicateUtil {
  fun build(condition: Condition) = when (condition.target) {
    Target.PathVariable -> parseConditionByPathVariable(condition.pattern)
  }

  private fun parseConditionByPathVariable(pattern: String) =
    RequestPredicate {
      val interpreter = ScriptEngineManager().getEngineByName("js")
      it.pathVariables().forEach { (key, value) -> interpreter.put(key, value) }
      interpreter.eval(pattern) as Boolean
    }

  fun build(baseUrl: String, cookie: Cookie) =
    method(cookie.method).and(path(baseUrl + cookie.urlPattern))
}

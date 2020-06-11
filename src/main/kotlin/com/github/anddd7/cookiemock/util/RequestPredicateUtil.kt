package com.github.anddd7.cookiemock.util

import com.github.anddd7.cookiemock.domain.CookieBox
import org.springframework.web.reactive.function.server.RequestPredicate
import org.springframework.web.reactive.function.server.RequestPredicates.method
import org.springframework.web.reactive.function.server.RequestPredicates.path

object RequestPredicateUtil {
  fun build(condition: String): RequestPredicate {
    // TODO build a customer dsl or language
    TODO()
  }

  fun build(baseUrl: String, cookieBox: CookieBox) =
    method(cookieBox.method).and(path(baseUrl + cookieBox.urlPattern))
}

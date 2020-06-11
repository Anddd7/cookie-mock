package com.github.anddd7.cookiemock.util

import com.github.anddd7.cookiemock.domain.CookieBox
import com.github.anddd7.cookiemock.domain.CookieCondition
import org.springframework.web.reactive.function.server.RequestPredicate
import org.springframework.web.reactive.function.server.RequestPredicates.method
import org.springframework.web.reactive.function.server.RequestPredicates.path

object RequestPredicateUtil {
  fun build(condition: CookieCondition): RequestPredicate {
    TODO()
  }

  fun build(baseUrl: String, cookieBox: CookieBox) =
    method(cookieBox.method).and(path(baseUrl + cookieBox.urlPattern))
}

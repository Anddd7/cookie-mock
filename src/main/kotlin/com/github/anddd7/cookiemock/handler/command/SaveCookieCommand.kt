package com.github.anddd7.cookiemock.handler.command

import com.github.anddd7.cookiemock.domain.Case
import com.github.anddd7.cookiemock.domain.Cookie
import com.github.anddd7.cookiemock.domain.CookieCondition
import org.springframework.http.HttpMethod

data class SaveCookieBoxCommand(
  val method: HttpMethod,
  val urlPattern: String,
  val cookies: List<SaveCookieCommand>
) {
  fun toCookieBox() = Cookie(
    method = method,
    urlPattern = urlPattern,
    cases = cookies.map(SaveCookieCommand::toCookie)
  )
}

data class SaveCookieCommand(
  val name: String,
  val condition: CookieCondition,
  val body: String
) {
  fun toCookie() = Case(
    name,
    condition,
    body
  )
}

package com.github.anddd7.cookiemock.handler.command

import com.github.anddd7.cookiemock.domain.Case
import com.github.anddd7.cookiemock.domain.Condition
import com.github.anddd7.cookiemock.domain.Cookie
import org.springframework.http.HttpMethod
import java.util.UUID

data class SaveCookieCommand(
  val boxId: UUID,
  val method: HttpMethod,
  val urlPattern: String,
  val cookies: List<SaveCaseCommand>
) {
  fun toCookieBox() = Cookie(
    boxId = boxId,
    method = method,
    urlPattern = urlPattern,
    cases = cookies.map(SaveCaseCommand::toCase)
  )
}

data class SaveCaseCommand(
  val name: String,
  val condition: Condition,
  val body: String
) {
  fun toCase() = Case(
    name = name,
    condition = condition,
    body = body
  )
}

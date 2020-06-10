package com.github.anddd7.cookiemock.handler

import com.github.anddd7.cookiemock.domain.CookieBox
import com.github.anddd7.cookiemock.domain.CookieBoxRepository
import kotlinx.coroutines.flow.firstOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.server.ResponseStatusException

@Service
class MockHandler(private val cookieBoxRepository: CookieBoxRepository) {
  val basePath = "/mock"

  suspend fun route(request: ServerRequest): ServerResponse {
    val cookie = cookieBoxRepository.findAll()
      .firstOrNull { it.isMatched(request) }
      ?.cookies
      ?.firstOrNull { it.condition.test(request) }
      ?: throw  ResponseStatusException(HttpStatus.NOT_FOUND)

    return ok().bodyValueAndAwait(cookie)
  }

  private fun CookieBox.isMatched(request: ServerRequest) =
    method == request.method() && path(basePath + urlPattern).test(request)
}

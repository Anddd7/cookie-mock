package com.github.anddd7.cookiemock.domain

import com.github.anddd7.cookiemock.util.RequestPredicateUtil
import kotlinx.coroutines.flow.firstOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.ResponseStatusException

class MockService(
  private val cookieBoxRepository: CookieBoxRepository
) {

  suspend fun route(request: ServerRequest): Cookie {
    return cookieBoxRepository.findAll()
      .firstOrNull { it.isMatched(request) }
      ?.cookies
      ?.firstOrNull {
        RequestPredicateUtil.toRequestPredicate(it.condition).test(request)
      }
      ?: throw  ResponseStatusException(HttpStatus.NOT_FOUND)
  }

  private fun CookieBox.isMatched(request: ServerRequest) =
    method == request.method() && RequestPredicates.path(Companion.basePath + urlPattern)
      .test(request)

  companion object {
    const val basePath = "/mock"
  }
}

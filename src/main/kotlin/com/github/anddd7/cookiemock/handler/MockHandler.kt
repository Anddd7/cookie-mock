package com.github.anddd7.cookiemock.handler

import com.github.anddd7.cookiemock.domain.CookieBoxRepository
import com.github.anddd7.cookiemock.domain.MockService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class MockHandler(cookieBoxRepository: CookieBoxRepository) {
  private val mockService: MockService by lazy { MockService(cookieBoxRepository) }

  suspend fun route(request: ServerRequest): ServerResponse {
    val cookie = mockService.route(request)
    return ok().bodyValueAndAwait(cookie)
  }
}

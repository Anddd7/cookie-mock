package com.github.anddd7.cookiemock.handler

import com.github.anddd7.cookiemock.domain.CookieRepository
import com.github.anddd7.cookiemock.util.RequestPredicateUtil.build
import kotlinx.coroutines.flow.firstOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Component
class MockHandler(private val cookieRepository: CookieRepository) {

  suspend fun route(request: ServerRequest): ServerResponse {
    val boxId = request.pathVariable("boxId")
    val body = cookieRepository.getByBoxId(UUID.fromString(boxId))
      .firstOrNull { build("/mock/${boxId}", it).test(request) }
      ?.cases
      ?.firstOrNull { build(it.condition).test(request) }
      ?.body
      ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    return ok().bodyValueAndAwait(body)
  }
}

package com.github.anddd7.cookiemock.handler

import com.github.anddd7.cookiemock.domain.Cookie
import com.github.anddd7.cookiemock.domain.CookieBox
import com.github.anddd7.cookiemock.domain.CookieBoxRepository
import com.github.anddd7.cookiemock.domain.EmptyUUID
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.RequestPredicate
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import java.util.UUID

@Service
class CookieHandler(private val cookieBoxRepository: CookieBoxRepository) {
  suspend fun getById(request: ServerRequest): ServerResponse {
    val uuid = UUID.fromString(request.pathVariable("id"))
    val cookieBox = cookieBoxRepository.get(uuid)
    return ok().bodyValueAndAwait(cookieBox)
  }

  suspend fun findAll(request: ServerRequest): ServerResponse {
    val cookieBoxes = cookieBoxRepository.findAll()
    return ok().bodyAndAwait(cookieBoxes)
  }

  suspend fun save(request: ServerRequest): ServerResponse {
    val command = request.bodyToMono<SaveCookieBoxCommand>().awaitFirst()
    val cookieBoxes = cookieBoxRepository.save(command.toCookieBox())
    return ok().bodyValueAndAwait(cookieBoxes)
  }

  suspend fun delete(request: ServerRequest): ServerResponse {
    val uuid = UUID.fromString(request.pathVariable("id"))
    cookieBoxRepository.delete(uuid)
    return ok().buildAndAwait()
  }
}

data class SaveCookieBoxCommand(
  val uuid: UUID = EmptyUUID
) {
  fun toCookieBox(): CookieBox {
    // TODO mock data
    return CookieBox(
      cookies = listOf(
        Cookie(
          name = "case 1",
          condition = RequestPredicate {
            it.pathVariable("id").toLong() > 0
          },
          body = "this is test response"
        )
      ),
      method = HttpMethod.GET,
      urlPattern = "/test/{id}"
    )
  }
}

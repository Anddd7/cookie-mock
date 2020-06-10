package com.github.anddd7.cookiemock.handler

import com.github.anddd7.cookiemock.domain.Cookie
import com.github.anddd7.cookiemock.domain.CookieBox
import com.github.anddd7.cookiemock.domain.CookieBoxRepository
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.RequestPredicate
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyAndAwait
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
//    val command = request.bodyToMono<SaveCookieBoxCommand>().awaitFirst()
//    val cookieBoxes = cookieBoxRepository.save(command.toCookieBox())
//    return ok().bodyValueAndAwait(cookieBoxes)
    cookieBoxRepository.save(
      CookieBox(
        method = HttpMethod.GET,
        urlPattern = "/number/{x}",
        cookies = listOf(
          Cookie(
            name = "x < 10",
            condition = RequestPredicate {
              it.pathVariable("x").toLong() < 10
            },
            body = "your number is less than 10"
          ),
          Cookie(
            name = "x < 100",
            condition = RequestPredicate {
              it.pathVariable("x").toLong() < 100
            },
            body = "your number is less than 100"
          ),
          Cookie(
            name = "x < 1000",
            condition = RequestPredicate {
              it.pathVariable("x").toLong() < 1000
            },
            body = "your number is less than 1000"
          )
        )
      )
    )
    return ok().bodyValueAndAwait("test")
  }

  suspend fun delete(request: ServerRequest): ServerResponse {
    val uuid = UUID.fromString(request.pathVariable("id"))
    cookieBoxRepository.delete(uuid)
    return ok().buildAndAwait()
  }
}

data class SaveCookieBoxCommand(
  val uuid: UUID?
) {
  fun toCookieBox(): CookieBox {
    TODO()
  }
}

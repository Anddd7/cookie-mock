package com.github.anddd7.cookiemock.handler

import com.github.anddd7.cookiemock.domain.CookieBoxRepository
import com.github.anddd7.cookiemock.domain.CookieService
import com.github.anddd7.cookiemock.handler.command.SaveCookieBoxCommand
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import java.util.UUID

@Component
class CookieHandler(
  cookieBoxRepository: CookieBoxRepository
) {
  private val cookieService: CookieService by lazy {
    CookieService(cookieBoxRepository)
  }

  suspend fun getById(request: ServerRequest): ServerResponse {
    val uuid = UUID.fromString(request.pathVariable("id"))
    val cookieBox = cookieService.getById(uuid)
    return ok().bodyValueAndAwait(cookieBox)
  }

  suspend fun findAll(request: ServerRequest): ServerResponse {
    val cookieBoxes = cookieService.findAll()
    return ok().bodyAndAwait(cookieBoxes)
  }

  suspend fun create(request: ServerRequest): ServerResponse = save(request)

  suspend fun save(request: ServerRequest): ServerResponse {
    val command = request.bodyToMono<SaveCookieBoxCommand>().awaitFirst()
    val uuid = request.pathVariables()["id"]?.let { UUID.fromString(it) }
    val cookieBoxes = cookieService.save(command.toCookieBox().copy(uuid = uuid))
    return ok().bodyValueAndAwait(cookieBoxes)
  }

  suspend fun delete(request: ServerRequest): ServerResponse {
    val uuid = UUID.fromString(request.pathVariable("id"))
    cookieService.delete(uuid)
    return ok().buildAndAwait()
  }
}

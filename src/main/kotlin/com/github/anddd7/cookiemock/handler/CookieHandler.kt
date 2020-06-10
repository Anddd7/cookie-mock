package com.github.anddd7.cookiemock.handler

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class CookieHandler {
  fun getById(request: ServerRequest): Mono<ServerResponse> {
    TODO()
  }

  fun findAll(request: ServerRequest): Mono<ServerResponse> {
    TODO()
  }

  fun save(request: ServerRequest): Mono<ServerResponse> {
    TODO()
  }

  fun delete(request: ServerRequest): Mono<ServerResponse> {
    TODO()
  }
}

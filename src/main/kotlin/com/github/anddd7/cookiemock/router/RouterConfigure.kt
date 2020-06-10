package com.github.anddd7.cookiemock.router

import com.github.anddd7.cookiemock.handler.CookieHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.coRouter


@Configuration
class RouterConfigure {

  @Bean
  fun router(cookieHandler: CookieHandler) = coRouter {
    GET("/ping") {
      ok().bodyValueAndAwait("pong")
    }
    "/cookie".nest {
      GET("/", cookieHandler::findAll)
      GET("/{id}", cookieHandler::getById)
      DELETE("/{id}", cookieHandler::delete)
      POST("/", cookieHandler::save)
    }
  }
}

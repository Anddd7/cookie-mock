package com.github.anddd7.cookiemock.router

import com.github.anddd7.cookiemock.handler.CookieHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfigure {

  private val cookieHandler = CookieHandler()

  @Bean
  fun router(): RouterFunction<ServerResponse> = router {
    GET("/ping") {
      ok().bodyValue("pong")
    }
    "/cookie".nest {
      GET("/", cookieHandler::findAll)
      GET("/{id}", cookieHandler::getById)
      DELETE("/{id}", cookieHandler::delete)
      POST("/", cookieHandler::save)
    }
  }
}

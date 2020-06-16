package com.github.anddd7.cookiemock.configuration

import com.github.anddd7.cookiemock.handler.CookieBoxHandler
import com.github.anddd7.cookiemock.handler.CookieHandler
import com.github.anddd7.cookiemock.handler.MockHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfigure {

  @Bean
  fun routerFunction(
    cookieHandler: CookieHandler,
    cookieBoxHandler: CookieBoxHandler,
    mockHandler: MockHandler
  ) = router {
    GET("/ping") { ok().bodyValue("pong") }
  }
    .and(apiRoutes(cookieHandler, cookieBoxHandler))
    .and(mockRoutes(mockHandler))

  private fun apiRoutes(
    cookieHandler: CookieHandler,
    cookieBoxHandler: CookieBoxHandler
  ) = coRouter {
    "/api".nest {
      "/cookie".nest {
        GET("/", cookieHandler::findAll)
        POST("/", cookieHandler::create)
        GET("/{id}", cookieHandler::get)
        POST("/{id}", cookieHandler::save)
        DELETE("/{id}", cookieHandler::delete)
      }
      "/box".nest {
        GET("/", cookieBoxHandler::findAll)
        POST("/", cookieBoxHandler::create)
        GET("/{id}", cookieBoxHandler::get)
        DELETE("/{id}", cookieBoxHandler::delete)
      }
    }
  }

  private fun mockRoutes(mockHandler: MockHandler) = coRouter {
    path("/mock/{boxId}/**", mockHandler::route)
  }
}

package com.github.anddd7.cookiemock.configuration

import com.github.anddd7.cookiemock.domain.MockService
import com.github.anddd7.cookiemock.handler.CookieHandler
import com.github.anddd7.cookiemock.handler.MockHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfigure {

  @Bean
  fun routerFunction(cookieHandler: CookieHandler, mockHandler: MockHandler) = router {
    GET("/ping") { ok().bodyValue("pong") }
  }
    .and(apiRoutes(cookieHandler))
    .and(mockRoutes(mockHandler))

  private fun apiRoutes(cookieHandler: CookieHandler) = coRouter {
    "/api".nest {
      "/cookie".nest {
        GET("/", cookieHandler::findAll)
        POST("/", cookieHandler::create)
        GET("/{id}", cookieHandler::getById)
        POST("/{id}", cookieHandler::save)
        DELETE("/{id}", cookieHandler::delete)
      }
    }
  }

  private fun mockRoutes(mockHandler: MockHandler) = coRouter {
    path(MockService.basePath + "/**", mockHandler::route)
  }
}
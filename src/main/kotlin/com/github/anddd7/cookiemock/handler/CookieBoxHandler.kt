package com.github.anddd7.cookiemock.handler

import com.github.anddd7.cookiemock.perimission.PermissionService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class CookieBoxHandler {
  /**
   * check permission before each operation
   */
  private val permissionService = PermissionService()

  suspend fun create(request: ServerRequest): ServerResponse = TODO()
  suspend fun delete(request: ServerRequest): ServerResponse = TODO()
  suspend fun get(request: ServerRequest): ServerResponse = TODO()
  suspend fun findAll(request: ServerRequest): ServerResponse = TODO()
}

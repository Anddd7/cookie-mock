package com.github.anddd7.cookiemock.domain

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpMethod
import java.util.UUID

data class Cookie(
  var uuid: UUID? = null,
  val boxId: UUID,
  val method: HttpMethod,
  val urlPattern: String,
  val cases: List<Case>
)

data class Case(
  val name: String,
  val condition: Condition,
  val body: String
)

data class Condition(
  val target: Target,
  val pattern: String
)

enum class Target {
  PathVariable,
}

interface CookieRepository {
  suspend fun save(cookie: Cookie): Cookie
  suspend fun get(uuid: UUID): Cookie
  suspend fun delete(uuid: UUID)
  fun findAll(): Flow<Cookie>

  suspend fun getByBoxId(uuid: UUID): Flow<Cookie>
}

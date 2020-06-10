package com.github.anddd7.cookiemock.domain

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpMethod
import org.springframework.web.reactive.function.server.RequestPredicate
import java.util.UUID

val EmptyUUID: UUID = UUID.fromString("empty")

data class CookieBox(
  var uuid: UUID = EmptyUUID,
  val cookies: List<Cookie>,
  val method: HttpMethod,
  val urlPattern: String
) {
  fun isNew() = uuid == EmptyUUID
}

data class Cookie(
  val name: String,
  val condition: RequestPredicate,
  val body: String
)

interface CookieBoxRepository {
  suspend fun save(cookieBox: CookieBox): CookieBox
  suspend fun get(uuid: UUID): CookieBox
  suspend fun delete(uuid: UUID)
  fun findAll(): Flow<CookieBox>
}

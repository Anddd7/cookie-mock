package com.github.anddd7.cookiemock.persistent

import com.github.anddd7.cookiemock.domain.Cookie
import com.github.anddd7.cookiemock.domain.CookieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Repository
class CookieMemoryRepoImpl : CookieRepository {
  private val db = ConcurrentHashMap<UUID, Cookie>()

  override suspend fun save(cookie: Cookie): Cookie {
    val uuid = cookie.uuid ?: UUID.randomUUID()
    db[uuid] = cookie.copy(uuid = uuid)
    return cookie
  }

  override suspend fun get(uuid: UUID) =
    db[uuid] ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

  override suspend fun delete(uuid: UUID) {
    db.remove(uuid) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
  }

  override fun findAll(): Flow<Cookie> = flow {
    db.values.forEach { emit(it) }
  }

  override suspend fun getByBoxId(uuid: UUID): Flow<Cookie> = flow {
    db.values
      .filter { it.boxId == uuid }
      .forEach { emit(it) }
  }
}

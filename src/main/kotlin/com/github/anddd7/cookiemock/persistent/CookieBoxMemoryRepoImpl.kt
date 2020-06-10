package com.github.anddd7.cookiemock.persistent

import com.github.anddd7.cookiemock.domain.CookieBox
import com.github.anddd7.cookiemock.domain.CookieBoxRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Repository
class CookieBoxMemoryRepoImpl : CookieBoxRepository {
  private val db = ConcurrentHashMap<UUID, CookieBox>()

  override suspend fun save(cookieBox: CookieBox): CookieBox {
    if (cookieBox.isNew()) {
      cookieBox.uuid = UUID.randomUUID()
    }
    db[cookieBox.uuid] = cookieBox
    return cookieBox
  }

  override suspend fun get(uuid: UUID) =
    db[uuid] ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

  override suspend fun delete(uuid: UUID) {
    db.remove(uuid) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
  }

  override fun findAll(): Flow<CookieBox> = flow {
    db.values.forEach { emit(it) }
  }
}

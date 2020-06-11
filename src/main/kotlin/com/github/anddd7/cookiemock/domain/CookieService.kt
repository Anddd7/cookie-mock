package com.github.anddd7.cookiemock.domain

import java.util.UUID

class CookieService(
  private val cookieRepository: CookieRepository
) {
  suspend fun getById(uuid: UUID) = cookieRepository.get(uuid)

  fun findAll() = cookieRepository.findAll()

  suspend fun save(cookie: Cookie) =
    cookieRepository.save(cookie)

  suspend fun delete(uuid: UUID) {
    cookieRepository.delete(uuid)
  }
}

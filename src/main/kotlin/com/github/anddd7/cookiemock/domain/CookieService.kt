package com.github.anddd7.cookiemock.domain

import java.util.UUID

class CookieService(
  private val cookieBoxRepository: CookieBoxRepository
) {
  suspend fun getById(uuid: UUID) = cookieBoxRepository.get(uuid)

  fun findAll() = cookieBoxRepository.findAll()

  suspend fun save(cookieBox: CookieBox) =
    cookieBoxRepository.save(cookieBox)

  suspend fun delete(uuid: UUID) {
    cookieBoxRepository.delete(uuid)
  }
}

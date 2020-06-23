package com.github.anddd7.cookiemock.domain

import java.util.UUID

data class CookieBox(
  var uuid: UUID? = null,
  val name: String,
  val tags: List<String>
)

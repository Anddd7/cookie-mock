package com.github.anddd7.cookiemock.perimission

import java.util.UUID

data class Permission(
  val type: PermissionType,
  val principal: UUID,
  val resource: UUID
)

enum class PermissionType {
  user, project
}

data class User(
  val uuid: UUID,
  val name: String,
  val project: Project
)

data class Project(
  val uuid: UUID,
  val name: String
)

package com.github.anddd7.cookiemock.perimission

class PermissionService {

  fun getAuthorizedResources(user: User) =
    getPermissions(user).map(Permission::resource)

  private fun getPermissions(user: User): List<Permission> {
    // TODO get by user id
    val userPermissions = emptyList<Permission>()
    // TODO get by project id
    val projectPermissions = emptyList<Permission>()

    return (userPermissions + projectPermissions).distinct()
  }
}

package pl.szczeliniak.kitchenassistant.user.queries.dto

import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination

data class UsersResponse(
    val users: Set<UserDto>,
    val pagination: Pagination
)
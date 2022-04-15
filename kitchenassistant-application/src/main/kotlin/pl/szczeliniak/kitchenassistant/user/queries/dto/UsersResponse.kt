package pl.szczeliniak.kitchenassistant.user.queries.dto

import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.Pagination

data class UsersResponse(
    val users: List<UserDto>,
    val pagination: Pagination
)
package pl.szczeliniak.kitchenassistant.user.dto

import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto

data class UserResponse(
    val user: UserDto
)
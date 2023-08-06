package pl.szczeliniak.kitchenassistant.user.dto.response

import pl.szczeliniak.kitchenassistant.shared.dtos.Page

data class UsersResponse(
    val users: Page<UserDto>,
) {
    data class UserDto(
        val id: Int,
        val email: String
    )
}
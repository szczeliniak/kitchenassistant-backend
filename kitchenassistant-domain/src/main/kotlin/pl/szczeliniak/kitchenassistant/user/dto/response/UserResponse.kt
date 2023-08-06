package pl.szczeliniak.kitchenassistant.user.dto.response

data class UserResponse(
    val user: UserDto
) {
    data class UserDto(
        val id: Int,
        val email: String
    )
}
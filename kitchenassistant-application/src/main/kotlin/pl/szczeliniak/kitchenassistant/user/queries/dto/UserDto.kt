package pl.szczeliniak.kitchenassistant.user.queries.dto

import pl.szczeliniak.kitchenassistant.user.User

data class UserDto(
    val id: Int,
    val email: String,
    val name: String
) {

    companion object {
        fun fromDomain(user: User): UserDto {
            return UserDto(user.id, user.email, user.name)
        }
    }

}
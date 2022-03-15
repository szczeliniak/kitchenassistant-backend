package pl.szczeliniak.kitchenassistant.user.queries.dto

import pl.szczeliniak.kitchenassistant.user.User
import java.time.LocalDateTime

data class UserDto(
    val id: Int,
    val email: String,
    val name: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {

    companion object {
        fun fromDomain(user: User): UserDto {
            return UserDto(user.id, user.email, user.name, user.createdAt, user.modifiedAt)
        }
    }

}
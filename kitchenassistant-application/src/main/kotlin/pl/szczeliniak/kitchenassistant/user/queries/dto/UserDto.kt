package pl.szczeliniak.kitchenassistant.user.queries.dto

import pl.szczeliniak.kitchenassistant.user.User

class UserDto {

    companion object {
        fun fromDomain(user: User): UserDto {
            val userDto = UserDto()
            userDto.id = user.id
            userDto.email = user.email
            userDto.name = user.name
            return userDto
        }
    }

    var id: Int = 0
    lateinit var email: String
    lateinit var name: String

}
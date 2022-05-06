package pl.szczeliniak.kitchenassistant.user.queries

import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto

open class UserConverter {

    open fun map(user: User): UserDto {
        return UserDto(user.id, user.email, user.name)
    }

}
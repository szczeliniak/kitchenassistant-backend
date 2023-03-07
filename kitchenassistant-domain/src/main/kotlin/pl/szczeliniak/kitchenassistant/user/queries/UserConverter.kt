package pl.szczeliniak.kitchenassistant.user.queries

import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto

open class UserConverter {

    open fun map(user: User): UserDto {
        return UserDto(id = user.id, email = user.email, name = user.name)
    }

}
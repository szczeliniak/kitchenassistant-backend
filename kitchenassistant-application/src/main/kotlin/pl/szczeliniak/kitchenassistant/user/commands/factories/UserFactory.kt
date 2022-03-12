package pl.szczeliniak.kitchenassistant.user.commands.factories

import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddNewUserDto

class UserFactory {

    fun create(dto: AddNewUserDto): User {
        return User(email = dto.email, password = dto.password, name = dto.name)
    }

}
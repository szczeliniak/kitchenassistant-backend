package pl.szczeliniak.kitchenassistant.user.commands.factories

import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddNewUserDto

open class UserFactory {

    open fun create(dto: AddNewUserDto): User {
        return User(email_ = dto.email, password_ = dto.password, name_ = dto.name)
    }

}
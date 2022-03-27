package pl.szczeliniak.kitchenassistant.user.commands.factories

import pl.szczeliniak.kitchenassistant.user.PasswordEncoder
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddUserDto

open class UserFactory(private val passwordEncoder: PasswordEncoder) {

    open fun create(dto: AddUserDto): User {
        return User(email_ = dto.email, password_ = passwordEncoder.encode(dto.password), name_ = dto.name)
    }

}
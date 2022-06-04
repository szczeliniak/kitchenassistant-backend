package pl.szczeliniak.kitchenassistant.user.commands.factories

import pl.szczeliniak.kitchenassistant.user.PasswordEncoder
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddUserDto
import pl.szczeliniak.kitchenassistant.user.commands.dto.RegisterDto

open class UserFactory(private val passwordEncoder: PasswordEncoder) {

    open fun create(dto: AddUserDto): User {
        return create(dto.email, dto.password, dto.name)
    }

    open fun create(email: String, password: String?, name: String): User {
        return User(email_ = email, password_ = password?.let { passwordEncoder.encode(it) }, name_ = name)
    }

    open fun create(dto: RegisterDto): User {
        return create(dto.email, dto.password, dto.name)
    }

}
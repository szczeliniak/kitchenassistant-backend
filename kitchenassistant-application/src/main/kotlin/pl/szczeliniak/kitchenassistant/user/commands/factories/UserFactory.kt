package pl.szczeliniak.kitchenassistant.user.commands.factories

import pl.szczeliniak.kitchenassistant.user.PasswordEncoder
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.commands.dto.RegisterDto

open class UserFactory(private val passwordEncoder: PasswordEncoder) {

    open fun create(email: String, password: String, name: String): User {
        return User(0, email, password.let { passwordEncoder.encode(it) }, name)
    }

    open fun create(dto: RegisterDto): User {
        return create(dto.email, dto.password, dto.name)
    }

}
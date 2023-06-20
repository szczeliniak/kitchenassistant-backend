package pl.szczeliniak.kitchenassistant.user.commands.factories

import pl.szczeliniak.kitchenassistant.user.PasswordEncoder
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.commands.dto.RegisterRequest

open class UserFactory(private val passwordEncoder: PasswordEncoder) {

    open fun create(email: String, password: String, name: String): User {
        return User(0, email, password.let { passwordEncoder.encode(it) }, name)
    }

    open fun create(request: RegisterRequest): User {
        return create(request.email, request.password, request.name)
    }

}
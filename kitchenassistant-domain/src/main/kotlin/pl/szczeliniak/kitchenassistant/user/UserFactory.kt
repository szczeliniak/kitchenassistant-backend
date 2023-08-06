package pl.szczeliniak.kitchenassistant.user

import pl.szczeliniak.kitchenassistant.user.dto.request.RegisterRequest
import pl.szczeliniak.kitchenassistant.user.db.User

open class UserFactory(private val passwordEncoder: PasswordEncoder) {

    open fun create(email: String, password: String): User {
        return User(0, email, password.let { passwordEncoder.encode(it) })
    }

    open fun create(request: RegisterRequest): User {
        return create(request.email, request.password)
    }

}
package pl.szczeliniak.kitchenassistant.user.queries

import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.user.PasswordMatcher
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse

open class GetUserByEmailAndPasswordQuery(private val userDao: UserDao, private val passwordMatcher: PasswordMatcher) {

    open fun execute(email: String, rawPassword: String): UserResponse {
        val user = userDao.findByEmail(email) ?: throw NotFoundException("User not found")
        user.validatePassword(rawPassword, passwordMatcher)
        return UserResponse(UserDto.fromDomain(user))
    }

}
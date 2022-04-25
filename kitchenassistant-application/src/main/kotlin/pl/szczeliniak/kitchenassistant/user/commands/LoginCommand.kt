package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.user.PasswordMatcher
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginDto
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginResponse
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory

class LoginCommand(
    private val userDao: UserDao,
    private val passwordMatcher: PasswordMatcher,
    private val tokenFactory: TokenFactory,
) {

    fun execute(dto: LoginDto): LoginResponse {
        val user = userDao.findByEmail(dto.email) ?: throw NotFoundException("User not found")
        user.validatePassword(dto.password, passwordMatcher)
        return LoginResponse(tokenFactory.create(user.id), user.id)
    }

}
package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
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
        val user = userDao.findByEmail(dto.email) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND)
        user.validatePassword(dto.password, passwordMatcher)
        val token = tokenFactory.create(user.id)
        return LoginResponse(token.token, user.id, token.validTo)
    }

}
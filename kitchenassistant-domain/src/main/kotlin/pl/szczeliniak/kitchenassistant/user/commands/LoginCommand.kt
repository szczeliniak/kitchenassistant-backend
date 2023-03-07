package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.user.PasswordMatcher
import pl.szczeliniak.kitchenassistant.user.db.UserCriteria
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginDto
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginResponse
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory

class LoginCommand(
        private val userDao: UserDao,
        private val passwordMatcher: PasswordMatcher,
        private val tokenFactory: TokenFactory,
) {

    fun execute(dto: LoginDto): LoginResponse {
        val user = userDao.findAll(UserCriteria(dto.email), 0, 1).firstOrNull() ?: throw KitchenAssistantException(
            ErrorCode.USER_NOT_FOUND
        )

        if (user.password?.let { !passwordMatcher.matches(it, dto.password) } == true) {
            throw KitchenAssistantException(ErrorCode.PASSWORDS_DO_NOT_MATCH)
        }
        val token = tokenFactory.create(user.id)
        return LoginResponse(token.token, user.id, token.validTo)
    }

}
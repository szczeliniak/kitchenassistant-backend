package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.user.db.UserCriteria
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginResponse
import pl.szczeliniak.kitchenassistant.user.commands.dto.RegisterDto
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory

class RegisterCommand(
    private val userFactory: UserFactory,
    private val tokenFactory: TokenFactory,
    private val userDao: UserDao
) {

    fun execute(dto: RegisterDto): LoginResponse {
        if (dto.password != dto.passwordRepeated) {
            throw KitchenAssistantException(ErrorCode.PASSWORDS_DO_NOT_MATCH)
        }

        if (userDao.findAll(UserCriteria(dto.email), 0, 1).firstOrNull() != null) {
            throw KitchenAssistantException(ErrorCode.USER_ALREADY_EXISTS)
        }

        val user = userDao.save(userFactory.create(dto))
        val token = tokenFactory.create(user.id)
        return LoginResponse(token.token, user.id, token.validTo)
    }

}
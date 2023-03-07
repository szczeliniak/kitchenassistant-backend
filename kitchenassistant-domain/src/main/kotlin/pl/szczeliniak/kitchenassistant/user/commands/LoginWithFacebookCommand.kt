package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.user.db.UserCriteria
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginResponse
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginWithFacebookDto
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory

class LoginWithFacebookCommand(
        private val userDao: UserDao,
        private val tokenFactory: TokenFactory,
        private val facebookConnector: FacebookConnector,
        private val userFactory: UserFactory,
) {

    fun execute(dto: LoginWithFacebookDto): LoginResponse {
        val user = facebookConnector.login(dto.token)?.let {
            userDao.findAll(UserCriteria(it.email!!), 0, 1).firstOrNull() ?: userDao.save(
                userFactory.create(
                    it.email,
                    "",
                    it.name!!
                )
            )
        } ?: throw KitchenAssistantException(ErrorCode.CANNOT_LOGIN_WITH_FACEBOOK)

        val token = tokenFactory.create(user.id)
        return LoginResponse(token.token, user.id, token.validTo)
    }

}
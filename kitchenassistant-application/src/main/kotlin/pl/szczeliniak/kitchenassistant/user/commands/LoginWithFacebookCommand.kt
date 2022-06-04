package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.user.UserDao
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
        val user = facebookConnector.login(dto.id, dto.token)?.let {
            userDao.findByEmail(it.email) ?: userDao.save(userFactory.create(it.email, null, it.name))
        } ?: throw NotFoundException("Cannot login with Facebook")

        val token = tokenFactory.create(user.id)
        return LoginResponse(token.token, user.id, token.validTo)
    }

}
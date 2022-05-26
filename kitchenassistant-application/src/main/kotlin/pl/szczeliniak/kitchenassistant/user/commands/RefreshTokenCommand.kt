package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.exceptions.MissingUserIdException
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.RefreshTokenResponse
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory

class RefreshTokenCommand(
    private val tokenFactory: TokenFactory,
    private val requestContext: RequestContext,
    private val userDao: UserDao
) {

    fun execute(): RefreshTokenResponse {
        val user = userDao.findById(requestContext.userId() ?: throw MissingUserIdException())
            ?: throw NotFoundException("User not found")
        val token = tokenFactory.create(user.id)
        return RefreshTokenResponse(token.token, token.validTo)
    }

}
package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.RefreshTokenResponse
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory

class RefreshTokenCommand(
    private val tokenFactory: TokenFactory,
    private val requestContext: RequestContext,
    private val userDao: UserDao
) {

    fun execute(): RefreshTokenResponse {
        val user =
            userDao.findById(requestContext.userId() ?: throw KitchenAssistantException(ErrorCode.MISSING_USER_ID))
                ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND)
        val token = tokenFactory.create(user.id)
        return RefreshTokenResponse(token.token, token.validTo)
    }

}
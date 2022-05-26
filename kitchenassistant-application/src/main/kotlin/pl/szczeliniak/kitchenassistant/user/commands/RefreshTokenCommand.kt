package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.exceptions.MissingUserIdException
import pl.szczeliniak.kitchenassistant.user.commands.dto.RefreshTokenResponse
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery

class RefreshTokenCommand(
    private val tokenFactory: TokenFactory,
    private val requestContext: RequestContext,
    private val getUserByIdQuery: GetUserByIdQuery
) {

    fun execute(): RefreshTokenResponse {
        val user = getUserByIdQuery.execute(requestContext.userId() ?: throw MissingUserIdException())
        return RefreshTokenResponse(tokenFactory.create(user.user.id))
    }

}
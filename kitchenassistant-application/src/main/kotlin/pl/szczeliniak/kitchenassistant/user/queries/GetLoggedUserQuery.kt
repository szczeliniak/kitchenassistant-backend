package pl.szczeliniak.kitchenassistant.user.queries

import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.exceptions.MissingUserIdException
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse

open class GetLoggedUserQuery(
    private val getUserByIdQuery: GetUserByIdQuery,
    private val requestContext: RequestContext
) {

    open fun execute(): UserResponse {
        return getUserByIdQuery.execute(requestContext.userId() ?: throw MissingUserIdException())
    }

}
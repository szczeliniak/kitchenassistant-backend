package pl.szczeliniak.kitchenassistant.user.queries

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse

open class GetLoggedUserQuery(
    private val userDao: UserDao,
    private val requestContext: RequestContext,
    private val userConverter: UserConverter
) {

    open fun execute(): UserResponse {
        return UserResponse(
            userConverter.map(
                userDao.findById(
                    requestContext.userId() ?: throw KitchenAssistantException(ErrorCode.MISSING_USER_ID)
                ) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND)
            )
        )
    }

}
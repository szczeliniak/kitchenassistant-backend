package pl.szczeliniak.kitchenassistant.user.queries

import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.exceptions.MissingUserIdException
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
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
                    requestContext.userId() ?: throw MissingUserIdException()
                ) ?: throw NotFoundException("User not found")
            )
        )
    }

}
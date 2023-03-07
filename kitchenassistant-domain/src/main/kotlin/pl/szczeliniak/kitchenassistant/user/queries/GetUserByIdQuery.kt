package pl.szczeliniak.kitchenassistant.user.queries

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse

open class GetUserByIdQuery(private val userDao: UserDao, private val userConverter: UserConverter) {

    open fun execute(userId: Int): UserResponse {
        return UserResponse(
            userConverter.map(
                userDao.findById(userId) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND)
            )
        )
    }

}
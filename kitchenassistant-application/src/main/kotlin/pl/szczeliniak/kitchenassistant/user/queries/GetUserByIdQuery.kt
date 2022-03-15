package pl.szczeliniak.kitchenassistant.user.queries

import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse

open class GetUserByIdQuery(private val userDao: UserDao) {

    open fun execute(userId: Int): UserResponse {
        return UserResponse(UserDto.fromDomain(userDao.findById(userId) ?: throw NotFoundException("User not found")))
    }

}
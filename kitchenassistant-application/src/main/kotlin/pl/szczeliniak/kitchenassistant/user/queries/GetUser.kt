package pl.szczeliniak.kitchenassistant.user.queries

import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto

class GetUser(private val userDao: UserDao) {

    fun execute(userId: Int): UserDto {
        return UserDto.fromDomain(userDao.findById(userId) ?: throw NotFoundException("User not found"))
    }

}
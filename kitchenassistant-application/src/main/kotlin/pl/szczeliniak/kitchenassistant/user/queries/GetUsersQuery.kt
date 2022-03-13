package pl.szczeliniak.kitchenassistant.user.queries

import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse

class GetUsersQuery(private val userDao: UserDao) {

    fun execute(): UsersResponse {
        return UsersResponse(userDao.findAll().map { UserDto.fromDomain(it) })
    }

}
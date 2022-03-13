package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddUserDto
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory

class AddUserCommand(private val userDao: UserDao, private val userFactory: UserFactory) {

    fun execute(dto: AddUserDto): SuccessResponse {
        userDao.save(userFactory.create(dto))
        return SuccessResponse()
    }

}
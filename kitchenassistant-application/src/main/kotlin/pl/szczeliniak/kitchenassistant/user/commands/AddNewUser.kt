package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddNewUserDto
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory

class AddNewUser(private val userDao: UserDao, private val userFactory: UserFactory) {

    fun execute(dto: AddNewUserDto): SuccessResponse {
        userDao.save(userFactory.create(dto))
        return SuccessResponse()
    }

}
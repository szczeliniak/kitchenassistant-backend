package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddNewUserDto
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory

class AddNewUser(private val userDao: UserDao, private val userFactory: UserFactory) {

    fun execute(dto: AddNewUserDto): Int {
        return userDao.save(userFactory.create(dto)).id
    }

}
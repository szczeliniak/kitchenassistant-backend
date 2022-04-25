package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.UserExistsException
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddUserDto
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory

open class AddUserCommand(private val userDao: UserDao, private val userFactory: UserFactory) {

    open fun execute(dto: AddUserDto): SuccessResponse {
        val user = userDao.findByEmail(dto.email)
        if (user != null) {
            throw UserExistsException("User with email already exists")
        }

        return SuccessResponse(userDao.save(userFactory.create(dto)).id)
    }

}
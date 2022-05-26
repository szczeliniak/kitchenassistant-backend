package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.exceptions.BadRequestException
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginResponse
import pl.szczeliniak.kitchenassistant.user.commands.dto.RegisterDto
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory

class RegisterCommand(
    private val userFactory: UserFactory,
    private val tokenFactory: TokenFactory,
    private val userDao: UserDao
) {

    fun execute(dto: RegisterDto): LoginResponse {
        if (dto.password != dto.passwordRepeated) {
            throw BadRequestException("Passwords do not match to each other")
        }

        val user = userFactory.create(dto)
        userDao.save(user)

        return LoginResponse(tokenFactory.create(user.id), user.id)
    }

}
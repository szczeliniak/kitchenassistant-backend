package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.exceptions.BadRequestException
import pl.szczeliniak.kitchenassistant.shared.exceptions.UserExistsException
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

        var user = userDao.findByEmail(dto.email)
        if (user != null) {
            throw UserExistsException("User with email already exists")
        }

        user = userDao.save(userFactory.create(dto))

        val token = tokenFactory.create(user.id)
        return LoginResponse(token.token, user.id, token.validTo)
    }

}
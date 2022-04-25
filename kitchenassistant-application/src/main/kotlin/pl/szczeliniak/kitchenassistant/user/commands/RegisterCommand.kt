package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.exceptions.BadRequestException
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddUserDto
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginResponse
import pl.szczeliniak.kitchenassistant.user.commands.dto.RegisterDto
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory

class RegisterCommand(
    private val addUserCommand: AddUserCommand,
    private val tokenFactory: TokenFactory,
) {

    fun execute(dto: RegisterDto): LoginResponse {
        if (dto.password != dto.passwordRepeated) {
            throw BadRequestException("Passwords do not match to each other")
        }

        val result = addUserCommand.execute(AddUserDto(dto.email, dto.password, dto.name))
        return LoginResponse(tokenFactory.create(result.id), result.id)
    }

}
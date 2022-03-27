package pl.szczeliniak.kitchenassistant.security.commands

import pl.szczeliniak.kitchenassistant.security.commands.dto.LoginDto
import pl.szczeliniak.kitchenassistant.security.commands.dto.LoginResponse
import pl.szczeliniak.kitchenassistant.security.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByEmailAndPasswordQuery

class LoginCommand(
    private val getUserByEmailAndPasswordQuery: GetUserByEmailAndPasswordQuery,
    private val tokenFactory: TokenFactory,
) {

    fun execute(dto: LoginDto): LoginResponse {
        val user = getUserByEmailAndPasswordQuery.execute(dto.email, dto.password).user
        return LoginResponse(tokenFactory.create(user.id))
    }

}
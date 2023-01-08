package pl.szczeliniak.kitchenassistant.user

import pl.szczeliniak.kitchenassistant.user.commands.LoginCommand
import pl.szczeliniak.kitchenassistant.user.commands.LoginWithFacebookCommand
import pl.szczeliniak.kitchenassistant.user.commands.RefreshTokenCommand
import pl.szczeliniak.kitchenassistant.user.commands.RegisterCommand
import pl.szczeliniak.kitchenassistant.user.commands.dto.*
import pl.szczeliniak.kitchenassistant.user.queries.GetLoggedUserQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUsersQuery
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse

open class UserFacadeImpl(
    private val getUserByIdQuery: GetUserByIdQuery,
    private val getLoggedUserQuery: GetLoggedUserQuery,
    private val getUsersQuery: GetUsersQuery,
    private val loginCommand: LoginCommand,
    private val loginWithFacebookCommand: LoginWithFacebookCommand,
    private val registerCommand: RegisterCommand,
    private val refreshTokenCommand: RefreshTokenCommand
) : UserFacade {

    override fun findById(id: Int): UserResponse {
        return getUserByIdQuery.execute(id)
    }

    override fun findAll(page: Long?, limit: Int?): UsersResponse {
        return getUsersQuery.execute(page, limit)
    }

    override fun login(dto: LoginDto): LoginResponse {
        return loginCommand.execute(dto)
    }

    override fun login(dto: LoginWithFacebookDto): LoginResponse {
        return loginWithFacebookCommand.execute(dto)
    }

    override fun register(dto: RegisterDto): LoginResponse {
        return registerCommand.execute(dto)
    }

    override fun refresh(): RefreshTokenResponse {
        return refreshTokenCommand.execute()
    }

    override fun getLoggedUser(): UserResponse {
        return getLoggedUserQuery.execute()
    }

}
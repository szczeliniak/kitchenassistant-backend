package pl.szczeliniak.kitchenassistant.user

import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.commands.*
import pl.szczeliniak.kitchenassistant.user.commands.dto.*
import pl.szczeliniak.kitchenassistant.user.queries.GetLoggedUserQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUsersQuery
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse

open class UserFacade(
    private val getUserByIdQuery: GetUserByIdQuery,
    private val getLoggedUserQuery: GetLoggedUserQuery,
    private val getUsersQuery: GetUsersQuery,
    private val addUserCommand: AddUserCommand,
    private val loginCommand: LoginCommand,
    private val loginWithFacebookCommand: LoginWithFacebookCommand,
    private val registerCommand: RegisterCommand,
    private val refreshTokenCommand: RefreshTokenCommand
) {

    open fun getUser(id: Int): UserResponse {
        return getUserByIdQuery.execute(id)
    }

    open fun getUsers(page: Long?, limit: Int?): UsersResponse {
        return getUsersQuery.execute(page, limit)
    }

    open fun addUser(dto: AddUserDto): SuccessResponse {
        return addUserCommand.execute(dto)
    }

    open fun login(dto: LoginDto): LoginResponse {
        return loginCommand.execute(dto)
    }

    open fun login(dto: LoginWithFacebookDto): LoginResponse {
        return loginWithFacebookCommand.execute(dto)
    }

    open fun register(dto: RegisterDto): LoginResponse {
        return registerCommand.execute(dto)
    }

    open fun refresh(): RefreshTokenResponse {
        return refreshTokenCommand.execute()
    }

    open fun getLoggedUser(): UserResponse {
        return getLoggedUserQuery.execute()
    }

}
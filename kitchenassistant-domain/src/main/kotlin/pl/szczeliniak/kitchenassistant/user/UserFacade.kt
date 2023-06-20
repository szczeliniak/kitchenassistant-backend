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

open class UserFacade(
        private val getUserByIdQuery: GetUserByIdQuery,
        private val getLoggedUserQuery: GetLoggedUserQuery,
        private val getUsersQuery: GetUsersQuery,
        private val loginCommand: LoginCommand,
        private val loginWithFacebookCommand: LoginWithFacebookCommand,
        private val registerCommand: RegisterCommand,
        private val refreshTokenCommand: RefreshTokenCommand
) {

    fun findById(id: Int): UserResponse {
        return getUserByIdQuery.execute(id)
    }

    fun findAll(page: Long?, limit: Int?): UsersResponse {
        return getUsersQuery.execute(page, limit)
    }

    fun login(request: LoginRequest): LoginResponse {
        return loginCommand.execute(request)
    }

    fun login(request: LoginWithFacebookRequest): LoginResponse {
        return loginWithFacebookCommand.execute(request)
    }

    fun register(request: RegisterRequest): LoginResponse {
        return registerCommand.execute(request)
    }

    fun refresh(): RefreshTokenResponse {
        return refreshTokenCommand.execute()
    }

    fun getLoggedUser(): UserResponse {
        return getLoggedUserQuery.execute()
    }

}
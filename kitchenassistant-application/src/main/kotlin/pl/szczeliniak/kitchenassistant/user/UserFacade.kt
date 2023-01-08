package pl.szczeliniak.kitchenassistant.user

import pl.szczeliniak.kitchenassistant.user.commands.dto.*
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse

interface UserFacade {

    fun findById(id: Int): UserResponse
    fun findAll(page: Long?, limit: Int?): UsersResponse
    fun login(dto: LoginDto): LoginResponse
    fun login(dto: LoginWithFacebookDto): LoginResponse
    fun register(dto: RegisterDto): LoginResponse
    fun refresh(): RefreshTokenResponse
    fun getLoggedUser(): UserResponse

}
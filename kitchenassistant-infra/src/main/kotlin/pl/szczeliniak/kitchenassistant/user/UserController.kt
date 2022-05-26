package pl.szczeliniak.kitchenassistant.user

import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.commands.AddUserCommand
import pl.szczeliniak.kitchenassistant.user.commands.LoginCommand
import pl.szczeliniak.kitchenassistant.user.commands.RefreshTokenCommand
import pl.szczeliniak.kitchenassistant.user.commands.RegisterCommand
import pl.szczeliniak.kitchenassistant.user.commands.dto.*
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUsersQuery
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    private val getUserByIdQuery: GetUserByIdQuery,
    private val getUsersQuery: GetUsersQuery,
    private val addUserCommand: AddUserCommand,
    private val loginCommand: LoginCommand,
    private val registerCommand: RegisterCommand,
    private val refreshTokenCommand: RefreshTokenCommand,
) {

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): UserResponse {
        return getUserByIdQuery.execute(id)
    }

    @GetMapping
    fun getUsers(
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?
    ): UsersResponse {
        return getUsersQuery.execute(page, limit)
    }

    @PostMapping
    fun addUser(@Valid @RequestBody dto: AddUserDto): SuccessResponse {
        return addUserCommand.execute(dto)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody dto: LoginDto): LoginResponse {
        return loginCommand.execute(dto)
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody dto: RegisterDto): LoginResponse {
        return registerCommand.execute(dto)
    }

    @PostMapping("/refresh")
    fun refresh(): RefreshTokenResponse {
        return refreshTokenCommand.execute()
    }

}
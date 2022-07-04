package pl.szczeliniak.kitchenassistant.user

import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.commands.dto.*
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    private val userFacade: UserFacade
) {

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): UserResponse {
        return userFacade.getUser(id)
    }

    @GetMapping
    fun getUsers(
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?
    ): UsersResponse {
        return userFacade.getUsers(page, limit)
    }

    @PostMapping
    fun addUser(@Valid @RequestBody dto: AddUserDto): SuccessResponse {
        return userFacade.addUser(dto)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody dto: LoginDto): LoginResponse {
        return userFacade.login(dto)
    }

    @PostMapping("/login/facebook")
    fun login(@Valid @RequestBody dto: LoginWithFacebookDto): LoginResponse {
        return userFacade.login(dto)
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody dto: RegisterDto): LoginResponse {
        return userFacade.register(dto)
    }

    @PostMapping("/refresh")
    fun refresh(): RefreshTokenResponse {
        return userFacade.refresh()
    }

    @GetMapping("/me")
    fun getLoggedUser(): UserResponse {
        return userFacade.getLoggedUser()
    }

}
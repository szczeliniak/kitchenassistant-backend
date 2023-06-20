package pl.szczeliniak.kitchenassistant.user

import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.security.AuthorizationService
import pl.szczeliniak.kitchenassistant.user.commands.dto.*
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    private val userFacade: UserFacade,
    private val authorizationService: AuthorizationService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): UserResponse {
        authorizationService.checkIsOwner(id)
        return userFacade.findById(id)
    }

    @GetMapping
    fun findAll(
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?
    ): UsersResponse {
        authorizationService.checkIsAdmin()
        return userFacade.findAll(page, limit)
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
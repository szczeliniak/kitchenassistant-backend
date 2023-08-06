package pl.szczeliniak.kitchenassistant.user

import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.user.dto.request.*
import pl.szczeliniak.kitchenassistant.user.dto.response.LoginResponse
import pl.szczeliniak.kitchenassistant.user.dto.response.RefreshTokenResponse
import pl.szczeliniak.kitchenassistant.user.dto.response.UserResponse
import pl.szczeliniak.kitchenassistant.user.dto.response.UsersResponse
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): UserResponse {
        return userService.findById(id)
    }

    @GetMapping
    fun findAll(
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?
    ): UsersResponse {
        return userService.findAll(page, limit)
    }

    @Transactional
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): LoginResponse {
        return userService.login(request)
    }

    @Transactional
    @PostMapping("/login/facebook")
    fun login(@Valid @RequestBody request: LoginWithFacebookRequest): LoginResponse {
        return userService.login(request)
    }

    @Transactional
    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): LoginResponse {
        return userService.register(request)
    }

    @Transactional
    @PostMapping("/refresh")
    fun refresh(): RefreshTokenResponse {
        return userService.refresh()
    }

    @GetMapping("/me")
    fun getLoggedUser(): UserResponse {
        return userService.getLoggedUser()
    }

}
package pl.szczeliniak.kitchenassistant.user

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.dto.request.LoginRequest
import pl.szczeliniak.kitchenassistant.user.dto.request.LoginWithFacebookRequest
import pl.szczeliniak.kitchenassistant.user.dto.request.RegisterRequest
import pl.szczeliniak.kitchenassistant.user.dto.request.ResetPasswordRequest
import pl.szczeliniak.kitchenassistant.user.dto.request.UpdatePasswordRequest
import pl.szczeliniak.kitchenassistant.user.dto.response.LoginResponse
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/users")
@Validated
class UserController(
    private val userService: UserService
) {

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
    fun refresh(): LoginResponse {
        return userService.refresh()
    }

    @Transactional
    @PutMapping("/password/reset")
    fun resetPassword(@Valid @RequestBody request: ResetPasswordRequest): SuccessResponse {
        return userService.resetPassword(request)
    }

    @Transactional
    @PutMapping("/password/update")
    fun updatePassword(@Valid @RequestBody request: UpdatePasswordRequest): SuccessResponse {
        return userService.updatePassword(request)
    }

}
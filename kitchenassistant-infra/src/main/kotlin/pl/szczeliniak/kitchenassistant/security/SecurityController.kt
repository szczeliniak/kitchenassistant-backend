package pl.szczeliniak.kitchenassistant.security

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.szczeliniak.kitchenassistant.security.commands.LoginCommand
import pl.szczeliniak.kitchenassistant.security.commands.RegisterCommand
import pl.szczeliniak.kitchenassistant.security.commands.dto.LoginDto
import pl.szczeliniak.kitchenassistant.security.commands.dto.LoginResponse
import pl.szczeliniak.kitchenassistant.security.commands.dto.RegisterDto

@RestController
class SecurityController(
    private val loginCommand: LoginCommand,
    private val registerCommand: RegisterCommand
) {

    @PostMapping("/login")
    fun login(@RequestBody dto: LoginDto): LoginResponse {
        return loginCommand.execute(dto)
    }

    @PostMapping("/register")
    fun register(@RequestBody dto: RegisterDto): LoginResponse {
        return registerCommand.execute(dto)
    }

}
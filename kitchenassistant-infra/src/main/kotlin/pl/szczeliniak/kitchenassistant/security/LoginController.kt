package pl.szczeliniak.kitchenassistant.security

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.szczeliniak.kitchenassistant.security.commands.LoginCommand
import pl.szczeliniak.kitchenassistant.security.commands.dto.LoginDto
import pl.szczeliniak.kitchenassistant.security.commands.dto.LoginResponse

@RestController
@RequestMapping("/login")
class LoginController(private val loginCommand: LoginCommand) {

    @PostMapping
    fun login(@RequestBody dto: LoginDto): LoginResponse {
        return loginCommand.execute(dto)
    }

}
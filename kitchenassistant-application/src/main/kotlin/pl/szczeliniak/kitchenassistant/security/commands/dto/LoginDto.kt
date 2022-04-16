package pl.szczeliniak.kitchenassistant.security.commands.dto

import org.hibernate.validator.constraints.Length

data class LoginDto(
    @field:Length(min = 1, max = 100) var email: String = "",
    @field:Length(min = 1, max = 100) var password: String = ""
)
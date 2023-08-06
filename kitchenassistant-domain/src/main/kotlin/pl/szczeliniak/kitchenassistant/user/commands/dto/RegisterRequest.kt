package pl.szczeliniak.kitchenassistant.user.commands.dto

import org.hibernate.validator.constraints.Length

data class RegisterRequest(
    @field:Length(min = 1, max = 100) var email: String = "",
    @field:Length(min = 1, max = 100) var password: String = "",
)
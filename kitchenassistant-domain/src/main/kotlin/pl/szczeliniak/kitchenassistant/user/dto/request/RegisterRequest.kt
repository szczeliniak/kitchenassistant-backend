package pl.szczeliniak.kitchenassistant.user.dto.request

import org.hibernate.validator.constraints.Length

data class RegisterRequest(
    @field:Length(min = 1, max = 50) var email: String = "",
    @field:Length(min = 1, max = 30) var password: String = "",
)
package pl.szczeliniak.kitchenassistant.user.dto.request

import org.hibernate.validator.constraints.Length

data class RegisterRequest(
    @field:Length(min = 1, max = 100) var email: String = "",
    @field:Length(min = 1, max = 100) var password: String = "",
)
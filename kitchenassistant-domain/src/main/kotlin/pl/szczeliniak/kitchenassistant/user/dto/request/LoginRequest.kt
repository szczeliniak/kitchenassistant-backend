package pl.szczeliniak.kitchenassistant.user.dto.request

import javax.validation.constraints.Size

data class LoginRequest(
    @field:Size(min = 1, max = 50) var email: String = "",
    @field:Size(min = 1, max = 30) var password: String = ""
)
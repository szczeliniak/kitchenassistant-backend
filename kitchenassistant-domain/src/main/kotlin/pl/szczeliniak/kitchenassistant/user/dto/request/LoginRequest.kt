package pl.szczeliniak.kitchenassistant.user.dto.request

import javax.validation.constraints.Size

data class LoginRequest(
    @field:Size(min = 5, max = 255) var email: String = "",
    @field:Size(min = 5, max = 255) var password: String = ""
)
package pl.szczeliniak.kitchenassistant.user.dto.request

import javax.validation.constraints.Size

data class ResetPasswordRequest(
    @field:Size(min = 1, max = 50) var email: String = ""
)
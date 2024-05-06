package pl.szczeliniak.cookbook.user.dto.request

import javax.validation.constraints.Size

data class ResetPasswordRequest(
    @field:Size(min = 5, max = 255) var email: String = ""
)
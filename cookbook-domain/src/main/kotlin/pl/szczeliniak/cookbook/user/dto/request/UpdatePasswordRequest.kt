package pl.szczeliniak.cookbook.user.dto.request

import javax.validation.constraints.Size

data class UpdatePasswordRequest(
    @field:Size(min = 5, max = 255) var oldPassword: String = "",
    @field:Size(min = 5, max = 255) var newPassword: String = ""
)
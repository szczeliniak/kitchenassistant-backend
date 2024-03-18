package pl.szczeliniak.kitchenassistant.user.dto.request

import javax.validation.constraints.Size

data class UpdatePasswordRequest(
    @field:Size(min = 5, max = 50) var oldPassword: String = "",
    @field:Size(min = 5, max = 50) var newPassword: String = ""
)
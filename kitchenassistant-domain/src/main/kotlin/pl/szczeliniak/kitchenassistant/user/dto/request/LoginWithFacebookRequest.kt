package pl.szczeliniak.kitchenassistant.user.dto.request

import javax.validation.constraints.Size

data class LoginWithFacebookRequest(
    @field:Size(min = 5, max = 500) var token: String = ""
)
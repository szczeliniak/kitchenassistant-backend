package pl.szczeliniak.kitchenassistant.user.dto.request

import org.hibernate.validator.constraints.Length

data class LoginWithFacebookRequest(
    @field:Length(min = 1, max = 500) var token: String = ""
)
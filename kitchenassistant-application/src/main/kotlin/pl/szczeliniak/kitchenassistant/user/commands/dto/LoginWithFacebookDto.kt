package pl.szczeliniak.kitchenassistant.user.commands.dto

import org.hibernate.validator.constraints.Length

data class LoginWithFacebookDto(
    @field:Length(min = 1, max = 500) var token: String = ""
)
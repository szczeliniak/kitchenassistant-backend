package pl.szczeliniak.kitchenassistant.user.dto.response

import java.time.ZonedDateTime

data class LoginResponse(
    var token: String = "",
    var id: Int = 0,
    var validTo: ZonedDateTime
)
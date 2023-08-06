package pl.szczeliniak.kitchenassistant.user.dto.response

import java.time.ZonedDateTime

data class RefreshTokenResponse(
    var token: String = "",
    var validTo: ZonedDateTime
)
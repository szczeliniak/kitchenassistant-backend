package pl.szczeliniak.kitchenassistant.user.commands.dto

import java.time.ZonedDateTime

data class RefreshTokenResponse(
    var token: String = "",
    var validTo: ZonedDateTime
)
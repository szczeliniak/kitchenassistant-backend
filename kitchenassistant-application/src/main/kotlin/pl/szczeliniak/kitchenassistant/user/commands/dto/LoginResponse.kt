package pl.szczeliniak.kitchenassistant.user.commands.dto

import java.time.LocalDateTime

data class LoginResponse(
    var token: String = "",
    var id: Int = 0,
    var validTo: LocalDateTime
)
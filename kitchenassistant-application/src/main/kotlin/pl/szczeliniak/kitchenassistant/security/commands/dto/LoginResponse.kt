package pl.szczeliniak.kitchenassistant.security.commands.dto

data class LoginResponse(
    var token: String = "",
    var id: Int = 0
)
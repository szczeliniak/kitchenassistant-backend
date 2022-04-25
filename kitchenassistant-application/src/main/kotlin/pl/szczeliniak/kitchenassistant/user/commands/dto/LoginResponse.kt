package pl.szczeliniak.kitchenassistant.user.commands.dto

data class LoginResponse(
    var token: String = "",
    var id: Int = 0
)
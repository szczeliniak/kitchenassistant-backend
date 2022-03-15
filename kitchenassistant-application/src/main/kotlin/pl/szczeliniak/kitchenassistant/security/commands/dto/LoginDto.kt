package pl.szczeliniak.kitchenassistant.security.commands.dto

data class LoginDto(
    var email: String = "",
    var password: String = ""
)
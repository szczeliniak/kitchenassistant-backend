package pl.szczeliniak.kitchenassistant.security.commands.dto

data class RegisterDto(
    var email: String = "",
    var name: String = "",
    var password: String = "",
    var passwordRepeated: String = ""
)
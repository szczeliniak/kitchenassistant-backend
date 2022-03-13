package pl.szczeliniak.kitchenassistant.user.commands.dto

data class AddUserDto(
    var email: String = "",
    var password: String = "",
    var name: String = ""
)
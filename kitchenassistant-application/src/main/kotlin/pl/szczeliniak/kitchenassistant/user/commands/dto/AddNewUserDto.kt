package pl.szczeliniak.kitchenassistant.user.commands.dto

data class AddNewUserDto(
    var email: String = "",
    var password: String = "",
    var name: String = ""
)
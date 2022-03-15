package pl.szczeliniak.kitchenassistant.security

data class LoggedUser(
    val token: String,
    val id: Int
)
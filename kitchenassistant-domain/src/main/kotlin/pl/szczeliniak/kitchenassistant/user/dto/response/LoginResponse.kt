package pl.szczeliniak.kitchenassistant.user.dto.response

data class LoginResponse(
    var accessToken: String,
    var refreshToken: String
)
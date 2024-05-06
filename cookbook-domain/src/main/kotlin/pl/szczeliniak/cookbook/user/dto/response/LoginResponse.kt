package pl.szczeliniak.cookbook.user.dto.response

data class LoginResponse(
    var accessToken: String,
    var refreshToken: String
)
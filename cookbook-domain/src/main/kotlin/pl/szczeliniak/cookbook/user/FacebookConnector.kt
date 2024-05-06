package pl.szczeliniak.cookbook.user

import pl.szczeliniak.cookbook.user.dto.response.FacebookLoginResponse

interface FacebookConnector {

    fun login(authToken: String): FacebookLoginResponse?

}
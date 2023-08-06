package pl.szczeliniak.kitchenassistant.user

import pl.szczeliniak.kitchenassistant.user.dto.response.FacebookLoginResponse

interface FacebookConnector {

    fun login(authToken: String): FacebookLoginResponse?

}
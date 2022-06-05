package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.user.commands.dto.FacebookLoginResponse

interface FacebookConnector {

    fun login(authToken: String): FacebookLoginResponse?

}
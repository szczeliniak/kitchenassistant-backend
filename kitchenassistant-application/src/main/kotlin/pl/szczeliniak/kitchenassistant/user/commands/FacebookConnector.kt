package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.user.commands.dto.FacebookLoginResponse

interface FacebookConnector {

    fun login(id: String, authToken: String): FacebookLoginResponse?

}
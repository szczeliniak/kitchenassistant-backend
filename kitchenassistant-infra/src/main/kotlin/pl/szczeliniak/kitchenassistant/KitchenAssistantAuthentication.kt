package pl.szczeliniak.kitchenassistant

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import java.util.*

class KitchenAssistantAuthentication(private val loggedUser: LoggedUser) : Authentication {

    private var authenticated = true

    override fun getName(): String {
        return loggedUser.token
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return Collections.emptyList()
    }

    override fun getCredentials(): Any {
        return Any()
    }

    override fun getDetails(): Any {
        return Any()
    }

    override fun getPrincipal(): Any {
        return Any()
    }

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.authenticated = isAuthenticated
    }

}
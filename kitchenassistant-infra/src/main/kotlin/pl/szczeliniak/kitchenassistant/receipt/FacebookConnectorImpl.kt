package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import pl.szczeliniak.kitchenassistant.user.commands.FacebookConnector
import pl.szczeliniak.kitchenassistant.user.commands.dto.FacebookLoginResponse

@Component
class FacebookConnectorImpl(private val restTemplate: RestTemplate) : FacebookConnector {

    companion object {
        private const val FACEBOOK_LOGIN_ENDPOINT =
            "https://graph.facebook.com/v14.0/me?fields=id,name,email&access_token=%s"
    }

    override fun login(authToken: String): FacebookLoginResponse? {
        return restTemplate.getForObject(
            String.format(FACEBOOK_LOGIN_ENDPOINT, authToken),
            FacebookLoginResponse::class.java
        )
    }

}
package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import pl.szczeliniak.kitchenassistant.user.commands.FacebookConnector
import pl.szczeliniak.kitchenassistant.user.commands.dto.FacebookLoginResponse

@Component
class FacebookConnectorImpl(private val restTemplate: RestTemplate) : FacebookConnector {

    companion object {
        private const val FACEBOOK_API_HOST = "https://graph.facebook.com/"
        private const val FACEBOOK_LOGIN_ENDPOINT = "%s?fields=id,name,email&access_token=%s"
    }

    override fun login(id: String, authToken: String): FacebookLoginResponse? {
        val url = String.format(FACEBOOK_API_HOST + FACEBOOK_LOGIN_ENDPOINT, id, authToken)
        return restTemplate.getForObject(url, FacebookLoginResponse::class.java)
    }

}
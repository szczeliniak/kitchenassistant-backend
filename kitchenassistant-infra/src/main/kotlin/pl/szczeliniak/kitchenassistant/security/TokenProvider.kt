package pl.szczeliniak.kitchenassistant.security

import org.apache.logging.log4j.util.Strings
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class TokenProvider(private val request: HttpServletRequest) {

    private val HEADER_AUTHORIZATION = "Authorization"
    private val BEARER_PREFIX = "Bearer "

    fun provide(): String? {
        val header = request.getHeader(HEADER_AUTHORIZATION)
        return if (header == null || !header.startsWith(BEARER_PREFIX)) null else header.replace(
            BEARER_PREFIX, Strings.EMPTY
        )
    }

}
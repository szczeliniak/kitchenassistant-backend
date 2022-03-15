package pl.szczeliniak.kitchenassistant.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthorizationFilter(
    private val tokenProvider: TokenProvider,
    private val tokenParser: TokenParser
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        tokenProvider.provide()?.let { token ->
            tokenParser.parse(token)?.let { loggedUser ->
                SecurityContextHolder.getContext().authentication = KitchenAssistantAuthentication(loggedUser)
            }
        }
        filterChain.doFilter(request, response)
    }

}
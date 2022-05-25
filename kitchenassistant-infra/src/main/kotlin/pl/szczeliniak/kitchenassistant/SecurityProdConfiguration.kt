package pl.szczeliniak.kitchenassistant

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class SecurityProdConfiguration(
    objectMapper: ObjectMapper,
    private val requestContext: RequestContext,
    private val getUserByIdQuery: GetUserByIdQuery,
    @Value("\${security.jwt.secret}") private val secret: String
) : SecurityConfiguration(objectMapper) {

    companion object {
        val PATH_WITHOUT_AUTHORIZATION = listOf("/users/login/**", "/users/register/**")
    }

    override fun configureHttpForEnv(http: HttpSecurity) {
        http.authorizeRequests().antMatchers(*PATH_WITHOUT_AUTHORIZATION.toTypedArray())
            .permitAll()
            .anyRequest()
            .authenticated()
        http.addFilterBefore(
            JwtAuthorizationFilter(),
            BasicAuthenticationFilter::class.java
        )
    }

    inner class JwtAuthorizationFilter : OncePerRequestFilter() {

        private var jwtParser: JwtParser = Jwts.parser().setSigningKey(secret)

        override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
        ) {
            if (PATH_WITHOUT_AUTHORIZATION.none { AntPathMatcher().match(it, request.requestURI) }) {
                try {
                    request.getHeader("X-Token")?.let { token ->
                        val userId = parseToken(token)
                        getUserByIdQuery.execute(userId)

                        requestContext.userId(userId)

                        SecurityContextHolder.getContext().authentication = KitchenAssistantAuthentication(token)
                    } ?: kotlin.run {
                        throw TokenException("Token is missing")
                    }
                } catch (e: Exception) {
                    writeException(response, e.message)
                    return
                }
            }
            filterChain.doFilter(request, response)
        }

        private fun parseToken(token: String): Int {
            try {
                return jwtParser.parseClaimsJws(token).body.subject.toInt()
            } catch (e: ExpiredJwtException) {
                throw TokenException("Token is expired")
            } catch (e: MalformedJwtException) {
                throw TokenException("Token is malformed")
            } catch (e: Exception) {
                throw TokenException("Unknown token error")
            }
        }

    }

    private class TokenException(message: String) : Exception(message)

}
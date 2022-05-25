package pl.szczeliniak.kitchenassistant

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import org.apache.logging.log4j.util.Strings
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class SecurityProdConfiguration(
    objectMapper: ObjectMapper,
    private val request: HttpServletRequest,
    private val getUserByIdQuery: GetUserByIdQuery,
    @Value("\${security.jwt.secret}") private val secret: String
) : SecurityConfiguration(objectMapper) {

    override fun configureHttpForEnv(http: HttpSecurity) {
        http.authorizeRequests().antMatchers("/users/login/**", "/users/register/**")
            .permitAll()
            .anyRequest()
            .authenticated()
        http.addFilterBefore(
            JwtAuthorizationFilter(TokenProvider(request), TokenParser(jwtParser(), getUserByIdQuery)),
            BasicAuthenticationFilter::class.java
        )
    }

    inner class JwtAuthorizationFilter(
        private val tokenProvider: TokenProvider,
        private val tokenParser: TokenParser
    ) : OncePerRequestFilter() {
        override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
        ) {
            try {
                tokenProvider.provide()?.let { token ->
                    SecurityContextHolder.getContext().authentication =
                        KitchenAssistantAuthentication(tokenParser.parse(token))
                } ?: kotlin.run {
                    throw TokenException("Token is missing")
                }
            } catch (e: Exception) {
                writeException(response, e.message)
                return
            }
            filterChain.doFilter(request, response)
        }
    }

    class TokenParser(private val jwtParser: JwtParser, private val getUserByIdQuery: GetUserByIdQuery) {
        fun parse(token: String): LoggedUser {
            try {
                val user = getUserByIdQuery.execute(jwtParser.parseClaimsJws(token).body.subject.toInt()).user
                return LoggedUser(token, user.id)
            } catch (e: ExpiredJwtException) {
                throw TokenException("Token is expired")
            } catch (e: MalformedJwtException) {
                throw TokenException("Token is malformed")
            } catch (e: Exception) {
                throw TokenException("Unknown token error")
            }
        }
    }

    class TokenProvider(private val request: HttpServletRequest) {

        companion object {
            private const val HEADER_AUTHORIZATION = "Authorization"
            private const val BEARER_PREFIX = "Bearer "
        }

        fun provide(): String? {
            val header = request.getHeader(HEADER_AUTHORIZATION)
            return if (header == null || !header.startsWith(BEARER_PREFIX)) null else header.replace(
                BEARER_PREFIX, Strings.EMPTY
            )
        }

    }

    private fun jwtParser(): JwtParser {
        return Jwts.parser().setSigningKey(secret)
    }

    private class TokenException(message: String) : Exception(message)

}
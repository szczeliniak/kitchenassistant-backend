package pl.szczeliniak.kitchenassistant

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.util.AntPathMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.filter.OncePerRequestFilter
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType
import pl.szczeliniak.kitchenassistant.user.TokenFactoryImpl.Companion.CLAIM_KEY_ID
import pl.szczeliniak.kitchenassistant.user.TokenFactoryImpl.Companion.CLAIM_KEY_TOKEN_TYPE
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class SecurityConfiguration(
    private val objectMapper: ObjectMapper,
    private val requestContext: RequestContext,
    private val userDao: UserDao,
    @Value("\${security.jwt.secret}") private val secret: String
) : WebSecurityConfigurerAdapter() {

    companion object {
        const val AUTH_HEADER = "Authorization"
        val PATHS_WITHOUT_AUTHORIZATION = listOf(
            "/users/login/**",
            "/users/register/**",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/"
        )
    }

    override fun configure(http: HttpSecurity?) {
        http?.let {
            http.csrf().disable()
            http.cors().configurationSource {
                val cors = CorsConfiguration()
                cors.allowedHeaders = listOf("*")
                cors.allowedMethods = listOf("*")
                cors.allowedOrigins = listOf("*")
                return@configurationSource cors
            }
            http.authorizeRequests().antMatchers(*PATHS_WITHOUT_AUTHORIZATION.toTypedArray())
                .permitAll()
                .anyRequest()
                .authenticated()
            http.addFilterBefore(
                JwtAuthorizationFilter(),
                BasicAuthenticationFilter::class.java
            )
            http.httpBasic().authenticationEntryPoint(authenticationEntryPoint())
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
    }

    private fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { _: HttpServletRequest?, httpServletResponse: HttpServletResponse, e: AuthenticationException ->
            writeException(httpServletResponse, e.message)
        }
    }

    protected fun writeException(response: HttpServletResponse, message: String?) {
        response.status = HttpStatus.FORBIDDEN.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.outputStream.write(objectMapper.writeValueAsBytes(ExceptionResponse(message)))
    }

    inner class JwtAuthorizationFilter : OncePerRequestFilter() {

        private var jwtParser: JwtParser = Jwts.parser().setSigningKey(secret)

        override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
        ) {
            if (PATHS_WITHOUT_AUTHORIZATION.none { AntPathMatcher().match(it, request.requestURI) }) {
                try {
                    request.getHeader(AUTH_HEADER)?.let { token ->
                        val claims = parseToken(token)
                        val userId = claims[CLAIM_KEY_ID] as Int
                        val type = TokenType.valueOf((claims[CLAIM_KEY_TOKEN_TYPE] as String))
                        userDao.findById(userId) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND)
                        requestContext.userId(userId)
                        requestContext.tokenType(type)
                        SecurityContextHolder.getContext().authentication = KitchenAssistantAuthentication(token)
                    } ?: kotlin.run {
                        throw KitchenAssistantException(ErrorCode.JWT_MISSING_TOKEN)
                    }
                } catch (e: KitchenAssistantException) {
                    writeException(response, e.message)
                    return
                }
            }
            filterChain.doFilter(request, response)
        }

        private fun parseToken(token: String): Claims {
            try {
                return jwtParser.parseClaimsJws(token).body
            } catch (e: ExpiredJwtException) {
                throw KitchenAssistantException(ErrorCode.JWT_EXPIRED_TOKEN)
            } catch (e: MalformedJwtException) {
                throw KitchenAssistantException(ErrorCode.JWT_MALFORMED_TOKEN)
            } catch (e: KitchenAssistantException) {
                throw KitchenAssistantException(ErrorCode.JWT_GENERIC_ERROR)
            }
        }

    }

}
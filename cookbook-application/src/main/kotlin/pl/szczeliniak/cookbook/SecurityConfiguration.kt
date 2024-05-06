package pl.szczeliniak.cookbook

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
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
import pl.szczeliniak.cookbook.shared.CookBookException
import pl.szczeliniak.cookbook.shared.ErrorCode
import pl.szczeliniak.cookbook.shared.RequestContext
import pl.szczeliniak.cookbook.shared.TokenType
import pl.szczeliniak.cookbook.user.TokenFactoryImpl.Companion.CLAIM_KEY_ID
import pl.szczeliniak.cookbook.user.TokenFactoryImpl.Companion.CLAIM_KEY_TOKEN_TYPE
import pl.szczeliniak.cookbook.user.db.UserDao
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
            "/users/password/reset",
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
        val errorCode = ErrorCode.FORBIDDEN
        response.status = errorCode.code
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.outputStream.write(objectMapper.writeValueAsBytes(ExceptionResponse(errorCode, message)))
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
                        userDao.findById(userId) ?: throw CookBookException(ErrorCode.USER_NOT_FOUND)
                        requestContext.userId(userId)
                        requestContext.tokenType(type)
                        SecurityContextHolder.getContext().authentication = CookBookAuthentication(token)
                    } ?: kotlin.run {
                        throw CookBookException(ErrorCode.JWT_MISSING_TOKEN)
                    }
                } catch (e: CookBookException) {
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
                throw CookBookException(ErrorCode.JWT_EXPIRED_TOKEN)
            } catch (e: MalformedJwtException) {
                throw CookBookException(ErrorCode.JWT_MALFORMED_TOKEN)
            } catch (e: CookBookException) {
                throw CookBookException(ErrorCode.JWT_GENERIC_ERROR)
            }
        }

    }

}
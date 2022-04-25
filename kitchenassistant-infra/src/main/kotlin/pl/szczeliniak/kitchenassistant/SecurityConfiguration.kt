package pl.szczeliniak.kitchenassistant

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class SecurityConfiguration(private val objectMapper: ObjectMapper) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.let {
            http.csrf().disable()
            http.cors()

            configureHttpForEnv(http)

            http.httpBasic().authenticationEntryPoint(authenticationEntryPoint())
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
    }

    abstract fun configureHttpForEnv(http: HttpSecurity)

    private fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { _: HttpServletRequest?, httpServletResponse: HttpServletResponse, e: AuthenticationException ->
            httpServletResponse.status = HttpStatus.FORBIDDEN.value()
            httpServletResponse.contentType = MediaType.APPLICATION_JSON_VALUE
            httpServletResponse.outputStream.write(objectMapper.writeValueAsBytes(ExceptionResponse(e.message)))
        }
    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers(
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/"
        )
    }

}
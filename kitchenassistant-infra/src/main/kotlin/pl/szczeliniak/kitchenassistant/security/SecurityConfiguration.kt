package pl.szczeliniak.kitchenassistant.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import pl.szczeliniak.kitchenassistant.exceptions.ExceptionResponse
import pl.szczeliniak.kitchenassistant.security.commands.LoginCommand
import pl.szczeliniak.kitchenassistant.security.commands.RegisterCommand
import pl.szczeliniak.kitchenassistant.security.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.commands.AddUserCommand
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByEmailAndPasswordQuery
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class SecurityConfiguration(private val objectMapper: ObjectMapper, private val secret: String) :
    WebSecurityConfigurerAdapter() {

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

    @Bean
    @Scope("prototype")
    open fun jwtParser(): JwtParser {
        return Jwts.parser().setSigningKey(secret)
    }

    @Bean
    @Scope("prototype")
    open fun jwtBuilder(): JwtBuilder {
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, secret)
    }

    @Bean
    open fun loginCommand(
        getUserByEmailAndPasswordQuery: GetUserByEmailAndPasswordQuery,
        tokenFactory: TokenFactory
    ): LoginCommand = LoginCommand(getUserByEmailAndPasswordQuery, tokenFactory)

    @Bean
    open fun registerCommand(addUserCommand: AddUserCommand, tokenFactory: TokenFactory): RegisterCommand =
        RegisterCommand(addUserCommand, tokenFactory)

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}
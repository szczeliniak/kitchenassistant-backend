package pl.szczeliniak.kitchenassistant.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Scope
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import pl.szczeliniak.kitchenassistant.exceptions.ExceptionResponse
import pl.szczeliniak.kitchenassistant.security.commands.LoginCommand
import pl.szczeliniak.kitchenassistant.security.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByEmailAndPasswordQuery
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class SecurityConfiguration(
    private val environment: Environment,
    private val objectMapper: ObjectMapper,
    @Value("\${security.jwt.secret}") private val secret: String,
    @Lazy private val jwtAuthorizationFilter: JwtAuthorizationFilter
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.let {
            http.csrf().disable()
            http.cors()

            if (isDevProfileEnabled()) {
                http.authorizeRequests().anyRequest().permitAll()
            } else {
                http.authorizeRequests().antMatchers("/login/**").permitAll().anyRequest().authenticated()
                http.addFilterBefore(jwtAuthorizationFilter, BasicAuthenticationFilter::class.java)
            }

            http.httpBasic().authenticationEntryPoint(authenticationEntryPoint())
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }

    }

    private fun isDevProfileEnabled(): Boolean {
        return listOf<String>(*environment.activeProfiles).contains("dev")
    }

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
    fun jwtParser(): JwtParser {
        return Jwts.parser().setSigningKey(secret)
    }

    @Bean
    @Scope("prototype")
    fun jwtBuilder(): JwtBuilder {
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, secret)
    }

    @Bean
    fun loginCommand(
        getUserByEmailAndPasswordQuery: GetUserByEmailAndPasswordQuery,
        tokenFactory: TokenFactory
    ): LoginCommand = LoginCommand(getUserByEmailAndPasswordQuery, tokenFactory)

}
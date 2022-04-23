package pl.szczeliniak.kitchenassistant.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
class ProdSecurityConfiguration(
    objectMapper: ObjectMapper,
    @Lazy private val jwtAuthorizationFilter: JwtAuthorizationFilter,
    @Value("\${security.jwt.secret}") private val secret: String
) : SecurityConfiguration(objectMapper, secret) {

    override fun configureHttpForEnv(http: HttpSecurity) {
        http.authorizeRequests().antMatchers("/login/**", "/register/**")
            .permitAll()
            .anyRequest()
            .authenticated()
        http.addFilterBefore(jwtAuthorizationFilter, BasicAuthenticationFilter::class.java)
    }

}
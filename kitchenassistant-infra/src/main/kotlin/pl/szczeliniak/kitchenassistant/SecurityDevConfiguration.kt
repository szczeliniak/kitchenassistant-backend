package pl.szczeliniak.kitchenassistant

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity

@Profile("dev")
@Primary
@Order(1)
@Configuration
open class SecurityDevConfiguration(objectMapper: ObjectMapper) : SecurityConfiguration(objectMapper) {

    override fun configureHttpForEnv(http: HttpSecurity) {
        http.authorizeRequests().anyRequest().permitAll()
    }

}
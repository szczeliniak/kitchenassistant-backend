package pl.szczeliniak.kitchenassistant.security

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
class DevSecurityConfiguration(objectMapper: ObjectMapper) : SecurityConfiguration(objectMapper, "dev-secret") {

    override fun configureHttpForEnv(http: HttpSecurity) {
        http.authorizeRequests().anyRequest().permitAll()
    }

}
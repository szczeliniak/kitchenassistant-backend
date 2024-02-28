package pl.szczeliniak.kitchenassistant.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.user.db.UserDao

@Configuration
class UserConfiguration {

    @Bean
    fun userFacade(
        userDao: UserDao,
        requestContext: RequestContext,
        passwordEncoder: org.springframework.security.crypto.password.PasswordEncoder,
        tokenFactory: TokenFactory,
        facebookConnector: FacebookConnector
    ): UserService {
        return UserService(
            userDao,
            matcher(passwordEncoder),
            tokenFactory,
            facebookConnector,
            requestContext,
            encoder(passwordEncoder)
        )
    }

    @Bean
    fun passwordEncoder(): org.springframework.security.crypto.password.PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    fun encoder(passwordEncoder: org.springframework.security.crypto.password.PasswordEncoder): PasswordEncoder =
        object : PasswordEncoder {
            override fun encode(rawPassword: String): String {
                return passwordEncoder.encode(rawPassword)
            }
        }

    fun matcher(passwordEncoder: org.springframework.security.crypto.password.PasswordEncoder): PasswordMatcher =
        object : PasswordMatcher {
            override fun matches(encryptedPassword: String, rawPassword: String): Boolean {
                return passwordEncoder.matches(rawPassword, encryptedPassword)
            }
        }

}
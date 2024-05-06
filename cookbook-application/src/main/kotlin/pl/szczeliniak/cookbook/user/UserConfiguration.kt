package pl.szczeliniak.cookbook.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pl.szczeliniak.cookbook.shared.RequestContext
import pl.szczeliniak.cookbook.user.db.UserDao

@Configuration
class UserConfiguration {

    companion object {
        private const val CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789"
        private const val PASSWORD_LENGTH = 20
    }

    @Bean
    fun userFacade(
        userDao: UserDao,
        requestContext: RequestContext,
        passwordEncoder: org.springframework.security.crypto.password.PasswordEncoder,
        tokenFactory: TokenFactory,
        facebookConnector: FacebookConnector,
        mailService: MailService,
        passwordGenerator: PasswordGenerator
    ): UserService {
        return UserService(
            userDao,
            matcher(passwordEncoder),
            tokenFactory,
            facebookConnector,
            requestContext,
            encoder(passwordEncoder),
            passwordGenerator,
            mailService
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

    @Bean
    fun passwordGenerator(): PasswordGenerator = object : PasswordGenerator {
        override fun generate(): String {
            val builder = StringBuilder()
            for (i in 0 until PASSWORD_LENGTH) {
                val charIndex: Int = (CHARACTERS.length * Math.random()).toInt()
                builder.append(CHARACTERS[charIndex])
            }
            return builder.toString()
        }
    }

}
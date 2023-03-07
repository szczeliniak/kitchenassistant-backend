package pl.szczeliniak.kitchenassistant.user

import org.springframework.stereotype.Component

@Component
class PasswordMatcherImpl(private val passwordEncoder: org.springframework.security.crypto.password.PasswordEncoder) :
        PasswordMatcher {

    override fun matches(encryptedPassword: String, rawPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encryptedPassword)
    }

}
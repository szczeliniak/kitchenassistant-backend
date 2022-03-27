package pl.szczeliniak.kitchenassistant.user

import org.springframework.stereotype.Component

@Component
class PasswordEncoderImpl(private val passwordEncoder: org.springframework.security.crypto.password.PasswordEncoder) :
    PasswordEncoder {

    override fun encode(rawPassword: String): String {
        return passwordEncoder.encode(rawPassword)
    }

}
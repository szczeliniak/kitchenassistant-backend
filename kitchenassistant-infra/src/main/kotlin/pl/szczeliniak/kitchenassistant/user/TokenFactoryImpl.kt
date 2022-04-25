package pl.szczeliniak.kitchenassistant.user

import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Component
class TokenFactoryImpl(
    @Value("\${security.jwt.secret}") private val secret: String
) : TokenFactory {

    companion object {

        private const val TOKEN_VALIDITY_DAYS = 14L

    }

    override fun create(userId: Int): String {
        return jwtBuilder().setSubject(userId.toString())
            .setIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
            .setExpiration(Date.from(LocalDateTime.now().plusDays(TOKEN_VALIDITY_DAYS).toInstant(ZoneOffset.UTC)))
            .compact()
    }

    private fun jwtBuilder(): JwtBuilder {
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, secret)
    }

}
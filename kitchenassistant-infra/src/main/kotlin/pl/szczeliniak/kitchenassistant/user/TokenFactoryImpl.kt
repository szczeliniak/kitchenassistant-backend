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

    override fun create(userId: Int): TokenFactory.Token {
        val expiration = LocalDateTime.now().plusDays(TOKEN_VALIDITY_DAYS)
        return TokenFactory.Token(
            jwtBuilder().setSubject(userId.toString())
                .setIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                .setExpiration(Date.from(expiration.toInstant(ZoneOffset.UTC)))
                .compact(), expiration
        )
    }

    private fun jwtBuilder(): JwtBuilder {
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, secret)
    }

}
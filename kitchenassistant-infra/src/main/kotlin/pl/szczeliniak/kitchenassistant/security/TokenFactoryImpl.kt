package pl.szczeliniak.kitchenassistant.security

import io.jsonwebtoken.JwtBuilder
import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.security.commands.factories.TokenFactory
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Component
class TokenFactoryImpl(private val jwtBuilder: JwtBuilder) : TokenFactory {

    private final val TOKEN_VALIDITY_DAYS = 14L

    override fun create(userId: Int): String {
        return jwtBuilder.setSubject(userId.toString())
            .setIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
            .setExpiration(Date.from(LocalDateTime.now().plusDays(TOKEN_VALIDITY_DAYS).toInstant(ZoneOffset.UTC)))
            .compact()
    }

}
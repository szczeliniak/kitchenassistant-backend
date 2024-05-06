package pl.szczeliniak.cookbook.user

import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import pl.szczeliniak.cookbook.shared.TokenType
import java.time.ZonedDateTime
import java.util.*

@Component
class TokenFactoryImpl(
    @Value("\${security.jwt.secret}") private val secret: String
) : TokenFactory {

    companion object {
        private const val ACCESS_TOKEN_VALIDITY_HOURS = 1L
        private const val REFRESH_TOKEN_VALIDITY_DAYS = 30L
        const val CLAIM_KEY_ID = "id"
        const val CLAIM_KEY_EMAIL = "email"
        const val CLAIM_KEY_TOKEN_TYPE = "type"
    }

    override fun create(userId: Int, email: String, tokenType: TokenType): String {
        if (tokenType == TokenType.REFRESH) {
            return refreshToken(userId, email);
        }
        return accessToken(userId, email);
    }

    private fun refreshToken(userId: Int, email: String): String {
        return jwtBuilder().setSubject(userId.toString())
            .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
            .setClaims(prepareClaims(userId, email, TokenType.REFRESH))
            .setExpiration(Date.from(ZonedDateTime.now().plusDays(REFRESH_TOKEN_VALIDITY_DAYS).toInstant())).compact();
    }

    private fun prepareClaims(userId: Int, email: String, tokenType: TokenType): Map<String, Any> {
        return mapOf(
            CLAIM_KEY_ID to userId,
            CLAIM_KEY_EMAIL to email,
            CLAIM_KEY_TOKEN_TYPE to tokenType.name
        )
    }

    private fun accessToken(userId: Int, email: String): String {
        return jwtBuilder().setSubject(userId.toString())
            .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
            .setClaims(prepareClaims(userId, email, TokenType.ACCESS))
            .setExpiration(Date.from(ZonedDateTime.now().plusDays(ACCESS_TOKEN_VALIDITY_HOURS).toInstant())).compact();
    }

    private fun jwtBuilder(): JwtBuilder {
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, secret)
    }

}
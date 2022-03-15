package pl.szczeliniak.kitchenassistant.security

import io.jsonwebtoken.JwtParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery

@Component
class TokenParser(private val jwtParser: JwtParser, private val getUserByIdQuery: GetUserByIdQuery) {

    private val logger: Logger = LoggerFactory.getLogger(TokenParser::class.java)

    fun parse(token: String): LoggedUser? {
        try {
            val user = getUserByIdQuery.execute(jwtParser.parseClaimsJws(token).body.subject.toInt()).user
            return LoggedUser(token, user.id)
        } catch (e: Exception) {
            logger.error(e.message, e)
        }
        return null
    }

}
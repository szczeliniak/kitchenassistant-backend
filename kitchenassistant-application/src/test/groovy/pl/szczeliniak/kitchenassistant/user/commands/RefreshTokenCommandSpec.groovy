package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.RefreshTokenResponse
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class RefreshTokenCommandSpec extends Specification {

    def userDao = Mock(UserDao)
    def tokenFactory = Mock(TokenFactory)
    def requestContext = Mock(RequestContext)

    @Subject
    def refreshTokenCommand = new RefreshTokenCommand(tokenFactory, requestContext, userDao)

    def 'should refresh token'() {
        given:
        requestContext.userId() >> 1
        userDao.findById(1) >> user()
        tokenFactory.create(1) >> "TOKEN"

        when:
        def result = refreshTokenCommand.execute()

        then:
        result == new RefreshTokenResponse("TOKEN")
    }

    User user() {
        return new User(1, "", "", "", LocalDateTime.now(), LocalDateTime.now())
    }

}

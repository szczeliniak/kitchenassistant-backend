package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.RefreshTokenResponse
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime
import java.time.Month

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
        tokenFactory.create(1) >> token()

        when:
        def result = refreshTokenCommand.execute()

        then:
        result == new RefreshTokenResponse("TOKEN", LocalDateTime.of(2022, Month.JANUARY, 1, 0, 0))
    }

    private static TokenFactory.Token token() {
        return new TokenFactory.Token("TOKEN", LocalDateTime.of(2022, Month.JANUARY, 1, 0, 0))
    }

    private static User user() {
        return new User(1, "", "", "", LocalDateTime.now(), LocalDateTime.now())
    }

}

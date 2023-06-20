package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.user.PasswordMatcher
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginRequest
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginResponse
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.db.UserCriteria
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZoneId
import java.time.ZonedDateTime

class LoginCommandSpec extends Specification {

    def userDao = Mock(UserDao)
    def passwordMatcher = Mock(PasswordMatcher)
    def tokenFactory = Mock(TokenFactory)
    @Subject
    def loginCommand = new LoginCommand(userDao, passwordMatcher, tokenFactory)

    def 'should login'() {
        given:
        userDao.findAll(new UserCriteria("MAIL"), 0, 1) >> Collections.singleton(user())
        passwordMatcher.matches("ENC_PASS", "PASS") >> true
        tokenFactory.create(1) >> token()

        when:
        def result = loginCommand.execute(loginRequest())

        then:
        result == new LoginResponse("TOKEN", 1, ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()))
    }

    def 'should throw exception when user not found'() {
        given:
        userDao.findAll(new UserCriteria("MAIL"), 0, 1) >> Collections.emptySet()
        when:
        loginCommand.execute(loginRequest())

        then:
        thrown(KitchenAssistantException)
    }

    def 'should throw exception when passwords do not match'() {
        given:
        userDao.findAll(new UserCriteria("MAIL"), 0, 1) >> Collections.singleton(user())
        passwordMatcher.matches("ENC_PASS", "PASS") >> false
        when:
        loginCommand.execute(loginRequest())

        then:
        thrown(KitchenAssistantException)
    }

    private static LoginRequest loginRequest() {
        return new LoginRequest("MAIL", "PASS")
    }

    private static User user() {
        return new User(1, "EMAIL", "ENC_PASS", "NAME", ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static TokenFactory.Token token() {
        return new TokenFactory.Token("TOKEN", ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()))
    }

}

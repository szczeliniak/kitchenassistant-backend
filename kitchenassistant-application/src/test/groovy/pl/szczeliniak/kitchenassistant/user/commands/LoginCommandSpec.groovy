package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.user.PasswordMatcher
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.UserCriteria
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginDto
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginResponse
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
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
        def result = loginCommand.execute(dto())

        then:
        result == new LoginResponse("TOKEN", 1, ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()))
    }

    def 'should throw exception when user not found'() {
        given:
        userDao.findAll(new UserCriteria("MAIL"), 0, 1) >> Collections.emptySet()
        when:
        loginCommand.execute(dto())

        then:
        thrown(KitchenAssistantException)
    }

    def 'should throw exception when passwords do not match'() {
        given:
        userDao.findAll(new UserCriteria("MAIL"), 0, 1) >> Collections.singleton(user())
        passwordMatcher.matches("ENC_PASS", "PASS") >> false
        when:
        loginCommand.execute(dto())

        then:
        thrown(KitchenAssistantException)
    }

    private static LoginDto dto() {
        return new LoginDto("MAIL", "PASS")
    }

    private static UserResponse userResponse() {
        return new UserResponse(new UserDto(1, "", ""))
    }

    private static User user() {
        return new User(1, "EMAIL", "ENC_PASS", "NAME", ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static TokenFactory.Token token() {
        return new TokenFactory.Token("TOKEN", ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()))
    }

}

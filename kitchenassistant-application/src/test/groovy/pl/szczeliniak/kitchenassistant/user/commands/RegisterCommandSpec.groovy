package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginResponse
import pl.szczeliniak.kitchenassistant.user.commands.dto.RegisterDto
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZoneId
import java.time.ZonedDateTime

class RegisterCommandSpec extends Specification {

    def userFactory = Mock(UserFactory)
    def tokenFactory = Mock(TokenFactory)
    def userDao = Mock(UserDao)

    @Subject
    def registerCommand = new RegisterCommand(userFactory, tokenFactory, userDao)

    def 'should register'() {
        given:
        def user = user()
        userFactory.create(registerDto()) >> user
        tokenFactory.create(2137) >> token()

        when:
        def result = registerCommand.execute(registerDto())

        then:
        result == new LoginResponse("TOKEN", 2137, ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()))
        1 * userDao.save(user)
    }

    private static RegisterDto registerDto() {
        return new RegisterDto("", "", "", "")
    }

    private static TokenFactory.Token token() {
        return new TokenFactory.Token("TOKEN", ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()))
    }

    private static User user() {
        return new User(2137, "", "", "", ZonedDateTime.now(), ZonedDateTime.now())
    }
}

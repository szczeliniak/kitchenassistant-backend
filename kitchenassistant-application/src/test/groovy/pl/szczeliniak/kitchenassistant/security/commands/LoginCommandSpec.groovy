package pl.szczeliniak.kitchenassistant.security.commands

import pl.szczeliniak.kitchenassistant.shared.exceptions.LoginException
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.user.PasswordMatcher
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.LoginCommand
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginDto
import pl.szczeliniak.kitchenassistant.user.commands.dto.LoginResponse
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class LoginCommandSpec extends Specification {

    def userDao = Mock(UserDao)
    def passwordMatcher = Mock(PasswordMatcher)
    def tokenFactory = Mock(TokenFactory)
    @Subject
    def loginCommand = new LoginCommand(userDao, passwordMatcher, tokenFactory)

    def 'should login'() {
        given:
        userDao.findByEmail("MAIL") >> user()
        passwordMatcher.matches("ENC_PASS", "PASS") >> true
        tokenFactory.create(1) >> "TOKEN"

        when:
        def result = loginCommand.execute(dto())

        then:
        result == new LoginResponse("TOKEN", 1)
    }

    def 'should throw exception when user not found'() {
        given:
        userDao.findByEmail("MAIL") >> null
        when:
        loginCommand.execute(dto())

        then:
        thrown(NotFoundException)
    }

    def 'should throw exception when passwords do not match'() {
        given:
        userDao.findByEmail("MAIL") >> user()
        passwordMatcher.matches("ENC_PASS", "PASS") >> false
        when:
        loginCommand.execute(dto())

        then:
        thrown(LoginException)
    }

    private static LoginDto dto() {
        return new LoginDto("MAIL", "PASS")
    }

    private static UserResponse userResponse() {
        return new UserResponse(new UserDto(1, "", ""))
    }

    private static User user() {
        return new User(1, "EMAIL", "ENC_PASS", "NAME", LocalDateTime.now(), LocalDateTime.now())
    }

}

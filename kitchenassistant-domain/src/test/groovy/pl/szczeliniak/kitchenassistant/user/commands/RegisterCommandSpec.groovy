package pl.szczeliniak.kitchenassistant.user.commands

import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.db.UserCriteria
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import pl.szczeliniak.kitchenassistant.user.dto.response.LoginResponse
import pl.szczeliniak.kitchenassistant.user.dto.request.RegisterRequest
import pl.szczeliniak.kitchenassistant.user.TokenFactory
import pl.szczeliniak.kitchenassistant.user.UserFactory
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
        userDao.findAll(new UserCriteria("EMAIL"), 0, 1) >> Collections.emptySet()
        userFactory.create(registerRequest()) >> user
        userDao.save(user) >> user
        tokenFactory.create(2137) >> token()

        when:
        def result = registerCommand.execute(registerRequest())

        then:
        result == new LoginResponse("TOKEN", 2137, ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()))
    }

    def 'should not register when user already exists'() {
        given:
        userDao.findAll(new UserCriteria("EMAIL"), 0, 1) >> Collections.singleton(user())

        when:
        registerCommand.execute(registerRequest())

        then:
        thrown(KitchenAssistantException)
    }

    private static RegisterRequest registerRequest() {
        return new RegisterRequest("EMAIL", "", "")
    }

    private static TokenFactory.Token token() {
        return new TokenFactory.Token("TOKEN", ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()))
    }

    private static User user() {
        return new User(2137, "", "", "", ZonedDateTime.now(), ZonedDateTime.now())
    }
}

package pl.szczeliniak.kitchenassistant.security.commands

import pl.szczeliniak.kitchenassistant.security.commands.dto.LoginDto
import pl.szczeliniak.kitchenassistant.security.commands.dto.LoginResponse
import pl.szczeliniak.kitchenassistant.security.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByEmailAndPasswordQuery
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import spock.lang.Specification
import spock.lang.Subject

class LoginCommandSpec extends Specification {

    def getUserByEmailAndPasswordQuery = Mock(GetUserByEmailAndPasswordQuery)
    def tokenFactory = Mock(TokenFactory)
    @Subject
    def loginCommand = new LoginCommand(getUserByEmailAndPasswordQuery, tokenFactory)

    def 'should login'() {
        given:
        getUserByEmailAndPasswordQuery.execute("MAIL", "PASSWORD") >> userResponse()
        tokenFactory.create(1) >> "TOKEN"

        when:
        def result = loginCommand.execute(dto())

        then:
        result == new LoginResponse("TOKEN", 1)
    }

    private static LoginDto dto() {
        return new LoginDto("MAIL", "PASSWORD")
    }

    private static UserResponse userResponse() {
        return new UserResponse(new UserDto(1, "", ""))
    }

}

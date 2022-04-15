package pl.szczeliniak.kitchenassistant.security.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.security.commands.dto.LoginResponse
import pl.szczeliniak.kitchenassistant.security.commands.dto.RegisterDto
import pl.szczeliniak.kitchenassistant.security.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.commands.AddUserCommand
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddUserDto
import spock.lang.Specification
import spock.lang.Subject

class RegisterCommandSpec extends Specification {

    def addUserCommand = Mock(AddUserCommand)
    def tokenFactory = Mock(TokenFactory)

    @Subject
    def registerCommand = new RegisterCommand(addUserCommand, tokenFactory)

    def 'should register'() {
        given:
        addUserCommand.execute(addUserDto()) >> new SuccessResponse(1)
        tokenFactory.create(1) >> "TOKEN"

        when:
        def result = registerCommand.execute(registerDto())

        then:
        result == new LoginResponse("TOKEN", 1)

    }

    private static RegisterDto registerDto() {
        return new RegisterDto("MAIL", "NAME", "PASS", "PASS")
    }

    private static AddUserDto addUserDto() {
        return new AddUserDto("MAIL", "PASS", "NAME")
    }

}

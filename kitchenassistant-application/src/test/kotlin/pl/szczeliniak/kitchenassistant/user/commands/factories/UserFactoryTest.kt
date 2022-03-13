package pl.szczeliniak.kitchenassistant.user.commands.factories

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddUserDto

internal class UserFactoryTest : JunitBaseClass() {

    @InjectMocks
    private lateinit var userFactory: UserFactory

    @Test
    fun shouldCreateUser() {
        val user = userFactory.create(dto())

        assertThat(user).usingRecursiveComparison()
            .ignoringFields("createdAt_", "modifiedAt_")
            .isEqualTo(user())
    }

    private fun user(): User {
        return User(email_ = "EMAIL", password_ = "PASS", name_ = "NAME")
    }

    private fun dto(): AddUserDto {
        return AddUserDto("EMAIL", "PASS", "NAME")
    }

}
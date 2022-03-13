package pl.szczeliniak.kitchenassistant.user.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddUserDto
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory

internal class AddUserCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var userDao: UserDao

    @Mock
    private lateinit var userFactory: UserFactory

    @InjectMocks
    private lateinit var addUserCommand: AddUserCommand

    @Test
    fun shouldAddUser() {
        val dto = dto()
        val user = user()

        whenever(userFactory.create(dto)).thenReturn(user)
        whenever(userDao.save(user)).thenReturn(user)

        val result = addUserCommand.execute(dto)

        assertThat(result).isEqualTo(SuccessResponse())
    }

    private fun user(): User {
        return User(email_ = "", password_ = "", name_ = "")
    }

    private fun dto(): AddUserDto {
        return AddUserDto("EMAIL", "PASS", "NAME")
    }

}
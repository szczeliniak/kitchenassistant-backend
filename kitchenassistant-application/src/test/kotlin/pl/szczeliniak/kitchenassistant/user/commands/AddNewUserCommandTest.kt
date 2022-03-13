package pl.szczeliniak.kitchenassistant.user.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddNewUserDto
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory

internal class AddNewUserCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var userDao: UserDao

    @Mock
    private lateinit var userFactory: UserFactory

    @InjectMocks
    private lateinit var addNewUserCommand: AddNewUserCommand

    @Test
    fun shouldAddNewUser() {
        val dto = dto()
        val user = user()

        whenever(userFactory.create(dto)).thenReturn(user)
        whenever(userDao.save(user)).thenReturn(user)

        val result = addNewUserCommand.execute(dto)

        assertThat(result).isEqualTo(SuccessResponse())
    }

    private fun user(): User {
        return User(email_ = "", password_ = "", name_ = "")
    }

    private fun dto(): AddNewUserDto {
        return AddNewUserDto("EMAIL", "PASS", "NAME")
    }

}
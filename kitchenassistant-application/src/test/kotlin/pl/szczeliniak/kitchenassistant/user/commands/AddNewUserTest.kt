package pl.szczeliniak.kitchenassistant.user.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.commands.dto.AddNewUserDto
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory

internal class AddNewUserTest : JunitBaseClass() {

    @Mock
    private lateinit var userDao: UserDao

    @Mock
    private lateinit var userFactory: UserFactory

    @InjectMocks
    private lateinit var addNewUser: AddNewUser

    @Test
    fun shouldAddNewUser() {
        val dto = dto()
        val user = user()

        whenever(userFactory.create(dto)).thenReturn(user)
        whenever(userDao.save(user)).thenReturn(user)

        val userId = addNewUser.execute(dto)

        assertThat(userId).isEqualTo(0)
    }

    private fun user(): User {
        return User(email = "", password = "", name = "")
    }

    private fun dto(): AddNewUserDto {
        return AddNewUserDto("EMAIL", "PASS", "NAME")
    }

}
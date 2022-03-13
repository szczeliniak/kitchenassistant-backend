package pl.szczeliniak.kitchenassistant.user.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import java.util.*

internal class GetUsersTest : JunitBaseClass() {

    @Mock
    private lateinit var userDao: UserDao

    @InjectMocks
    private lateinit var getUsers: GetUsers

    @Test
    fun shouldReturnUsers() {
        whenever(userDao.findAll()).thenReturn(Collections.singletonList(user()))

        val result = getUsers.execute()

        assertThat(result).isEqualTo(Collections.singletonList(userDto()))
    }

    private fun userDto(): UserDto {
        return UserDto(id = 0, email = "EMAIL", name = "NAME")
    }

    private fun user(): User {
        return User(email_ = "EMAIL", name_ = "NAME", password_ = "")
    }
}
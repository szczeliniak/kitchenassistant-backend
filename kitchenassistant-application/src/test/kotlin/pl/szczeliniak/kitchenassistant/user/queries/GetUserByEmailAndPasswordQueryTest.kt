package pl.szczeliniak.kitchenassistant.user.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.exceptions.LoginException
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import java.time.LocalDateTime

internal class GetUserByEmailAndPasswordQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var userDao: UserDao

    @InjectMocks
    private lateinit var getUserByEmailAndPasswordQuery: GetUserByEmailAndPasswordQuery

    @Test
    fun shouldReturnUserByEmailAndPassword() {
        whenever(userDao.findByEmail("MAIL")).thenReturn(user())

        val result = getUserByEmailAndPasswordQuery.execute("MAIL", "PASS")

        assertThat(result).isEqualTo(UserResponse(userDto()))
    }

    @Test
    fun shouldThrowExceptionWhenUserNotFound() {
        whenever(userDao.findByEmail("MAIL")).thenReturn(null)

        assertThatThrownBy { getUserByEmailAndPasswordQuery.execute("MAIL", "PASS") }
            .isInstanceOf(NotFoundException::class.java)
            .hasMessage("User not found")
    }

    @Test
    fun shouldThrowExceptionWhenPasswordsDoNotMatch() {
        whenever(userDao.findByEmail("MAIL")).thenReturn(user())

        assertThatThrownBy { getUserByEmailAndPasswordQuery.execute("MAIL", "OTHER_PASS") }
            .isInstanceOf(LoginException::class.java)
            .hasMessage("Passwords do not match")
    }

    private fun userDto(): UserDto {
        return UserDto(0, "EMAIL", "NAME")
    }

    private fun user(): User {
        return User(0, "EMAIL", "PASS", "NAME", LocalDateTime.now(), LocalDateTime.now())
    }

}
package pl.szczeliniak.kitchenassistant.user.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.exceptions.LoginException
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.user.PasswordMatcher
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse

internal class GetUserByEmailAndPasswordQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var userDao: UserDao

    @Mock
    private lateinit var passwordMatcher: PasswordMatcher

    @InjectMocks
    private lateinit var getUserByEmailAndPasswordQuery: GetUserByEmailAndPasswordQuery

    @Test
    fun shouldReturnUserByEmailAndPassword() {
        whenever(userDao.findByEmail("MAIL")).thenReturn(user())
        whenever(passwordMatcher.matches("ENC_PASS", "PASS")).thenReturn(true)
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
        whenever(passwordMatcher.matches("ENC_PASS", "PASS")).thenReturn(false)

        assertThatThrownBy { getUserByEmailAndPasswordQuery.execute("MAIL", "PASS") }
            .isInstanceOf(LoginException::class.java)
            .hasMessage("Passwords do not match")
    }

    private fun userDto(): UserDto {
        return UserDto(0, "EMAIL", "NAME")
    }

    private fun user(): User {
        return User(0, "EMAIL", "ENC_PASS", "NAME")
    }

}
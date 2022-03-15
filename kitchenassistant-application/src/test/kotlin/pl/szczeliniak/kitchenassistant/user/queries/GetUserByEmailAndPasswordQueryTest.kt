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
        val createdAt = LocalDateTime.now()
        val modifiedAt = LocalDateTime.now()
        whenever(userDao.findByEmail("MAIL")).thenReturn(user(createdAt, modifiedAt))

        val result = getUserByEmailAndPasswordQuery.execute("MAIL", "PASS")

        assertThat(result).isEqualTo(UserResponse(userDto(createdAt, modifiedAt)))
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
        whenever(userDao.findByEmail("MAIL")).thenReturn(user(LocalDateTime.now(), LocalDateTime.now()))

        assertThatThrownBy { getUserByEmailAndPasswordQuery.execute("MAIL", "OTHER_PASS") }
            .isInstanceOf(LoginException::class.java)
            .hasMessage("Passwords do not match")
    }

    private fun userDto(createdAt: LocalDateTime, modifiedAt: LocalDateTime): UserDto {
        return UserDto(0, "EMAIL", "NAME", createdAt, modifiedAt)
    }

    private fun user(createdAt: LocalDateTime, modifiedAt: LocalDateTime): User {
        return User(0, "EMAIL", "PASS", "NAME", createdAt, modifiedAt)
    }

}
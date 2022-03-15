package pl.szczeliniak.kitchenassistant.user.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import java.time.LocalDateTime

internal class GetUserByIdQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var userDao: UserDao

    @InjectMocks
    private lateinit var getUserByIdQuery: GetUserByIdQuery

    @Test
    fun shouldReturnUser() {
        val createdAt = LocalDateTime.now()
        val modifiedAt = LocalDateTime.now()
        val user = user(createdAt, modifiedAt)
        whenever(userDao.findById(1)).thenReturn(user)

        val result = getUserByIdQuery.execute(1)

        assertThat(result).isEqualTo(UserResponse(userDto(createdAt, modifiedAt)))
    }

    @Test
    fun shouldThrowExceptionWhenUserNotFound() {
        whenever(userDao.findById(1)).thenReturn(null)

        assertThatThrownBy { getUserByIdQuery.execute(1) }
            .isInstanceOf(NotFoundException::class.java)
            .hasMessage("User not found")
    }

    private fun userDto(createdAt: LocalDateTime, modifiedAt: LocalDateTime): UserDto {
        return UserDto(0, "EMAIL", "NAME", createdAt, modifiedAt)
    }

    private fun user(createdAt: LocalDateTime, modifiedAt: LocalDateTime): User {
        return User(email_ = "EMAIL", password_ = "", name_ = "NAME", createdAt_ = createdAt, modifiedAt_ = modifiedAt)
    }

}
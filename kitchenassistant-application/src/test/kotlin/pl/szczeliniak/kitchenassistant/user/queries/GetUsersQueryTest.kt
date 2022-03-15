package pl.szczeliniak.kitchenassistant.user.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse
import java.time.LocalDateTime
import java.util.*

internal class GetUsersQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var userDao: UserDao

    @InjectMocks
    private lateinit var getUsersQuery: GetUsersQuery

    @Test
    fun shouldReturnUsers() {
        val createdAt = LocalDateTime.now()
        val modifiedAt = LocalDateTime.now()
        whenever(userDao.findAll()).thenReturn(Collections.singletonList(user(createdAt, modifiedAt)))

        val result = getUsersQuery.execute()

        assertThat(result).isEqualTo(UsersResponse(Collections.singletonList(userDto(createdAt, modifiedAt))))
    }

    private fun userDto(createdAt: LocalDateTime, modifiedAt: LocalDateTime): UserDto {
        return UserDto(id = 0, email = "EMAIL", name = "NAME", createdAt, modifiedAt)
    }

    private fun user(createdAt: LocalDateTime, modifiedAt: LocalDateTime): User {
        return User(email_ = "EMAIL", name_ = "NAME", password_ = "", createdAt_ = createdAt, modifiedAt_ = modifiedAt)
    }
}
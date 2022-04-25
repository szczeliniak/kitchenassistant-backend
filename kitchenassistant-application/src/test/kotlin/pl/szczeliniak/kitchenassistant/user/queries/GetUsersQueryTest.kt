package pl.szczeliniak.kitchenassistant.user.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination
import pl.szczeliniak.kitchenassistant.user.User
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse

internal class GetUsersQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var userDao: UserDao

    @InjectMocks
    private lateinit var getUsersQuery: GetUsersQuery

    @Test
    fun shouldReturnUsers() {
        whenever(userDao.findAll(100, 25)).thenReturn(mutableSetOf(user()))
        whenever(userDao.count()).thenReturn(280)
        val result = getUsersQuery.execute(5, 25)

        assertThat(result).isEqualTo(UsersResponse(mutableSetOf(userDto()), Pagination(5, 25, 12)))
    }

    private fun userDto(): UserDto {
        return UserDto(id = 0, email = "EMAIL", name = "NAME")
    }

    private fun user(): User {
        return User(email_ = "EMAIL", name_ = "NAME", password_ = "")
    }
}
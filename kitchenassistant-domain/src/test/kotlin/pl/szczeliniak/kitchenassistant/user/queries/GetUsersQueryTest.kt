package pl.szczeliniak.kitchenassistant.user.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.db.UserCriteria
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse

internal class GetUsersQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var userDao: UserDao

    @Mock
    private lateinit var userConverter: UserConverter

    @InjectMocks
    private lateinit var getUsersQuery: GetUsersQuery

    @Test
    fun shouldReturnUsers() {
        val user = user()
        val userDto = userDto()
        val criteria = UserCriteria()
        whenever(userDao.findAll(criteria, 100, 25)).thenReturn(setOf(user))
        whenever(userDao.count(criteria)).thenReturn(280)
        whenever(userConverter.map(user)).thenReturn(userDto)

        val result = getUsersQuery.execute(5, 25)

        assertThat(result).isEqualTo(UsersResponse(setOf(userDto), Pagination(5, 25, 12)))
    }

    private fun userDto(): UserDto {
        return UserDto(id = 0, email = "", name = "")
    }

    private fun user(): User {
        return User(email = "", name = "", password = "")
    }
}
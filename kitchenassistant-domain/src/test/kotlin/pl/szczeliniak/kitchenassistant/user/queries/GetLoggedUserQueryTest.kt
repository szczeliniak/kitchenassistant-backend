package pl.szczeliniak.kitchenassistant.user.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse

internal class GetLoggedUserQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var userDao: UserDao

    @Mock
    private lateinit var userConverter: UserConverter

    @Mock
    private lateinit var requestContext: RequestContext

    @InjectMocks
    private lateinit var getLoggedUserQuery: GetLoggedUserQuery

    @Test
    fun shouldReturnUser() {
        val user = user()
        val userDto = userDto()
        whenever(requestContext.userId()).thenReturn(1)
        whenever(userDao.findById(1)).thenReturn(user)
        whenever(userConverter.map(user)).thenReturn(userDto)

        val result = getLoggedUserQuery.execute()

        assertThat(result).isEqualTo(UserResponse(userDto))
    }

    private fun userDto(): UserDto {
        return UserDto(0, "")
    }

    private fun user(): User {
        return User(0, "", "")
    }

}
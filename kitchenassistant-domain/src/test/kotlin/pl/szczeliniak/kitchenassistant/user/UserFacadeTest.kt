package pl.szczeliniak.kitchenassistant.user

import org.assertj.core.util.Sets
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination
import pl.szczeliniak.kitchenassistant.user.commands.LoginCommand
import pl.szczeliniak.kitchenassistant.user.commands.LoginWithFacebookCommand
import pl.szczeliniak.kitchenassistant.user.commands.RefreshTokenCommand
import pl.szczeliniak.kitchenassistant.user.commands.RegisterCommand
import pl.szczeliniak.kitchenassistant.user.commands.dto.*
import pl.szczeliniak.kitchenassistant.user.queries.GetLoggedUserQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUsersQuery
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserResponse
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse
import java.time.ZonedDateTime

internal class UserFacadeTest : JunitBaseClass() {

    @InjectMocks
    private lateinit var userFacade: UserFacade

    @Mock
    private lateinit var getUserByIdQuery: GetUserByIdQuery

    @Mock
    private lateinit var getLoggedUserQuery: GetLoggedUserQuery

    @Mock
    private lateinit var getUsersQuery: GetUsersQuery

    @Mock
    private lateinit var loginCommand: LoginCommand

    @Mock
    private lateinit var loginWithFacebookCommand: LoginWithFacebookCommand

    @Mock
    private lateinit var registerCommand: RegisterCommand

    @Mock
    private lateinit var refreshTokenCommand: RefreshTokenCommand

    @Test
    fun shouldReturnUserById() {
        whenever(getUserByIdQuery.execute(1)).thenReturn(UserResponse(UserDto(1, "email", "name")))

        val result = userFacade.findById(1)

        Assertions.assertEquals(UserResponse(UserDto(1, "email", "name")), result)
    }

    @Test
    fun shouldReturnUsers() {
        whenever(getUsersQuery.execute(1L, 1)).thenReturn(UsersResponse(Sets.newLinkedHashSet(UserDto(1, "email", "name")), Pagination(1, 1, 1L)))

        val result = userFacade.findAll(1L, 1)

        Assertions.assertEquals(UsersResponse(Sets.newLinkedHashSet(UserDto(1, "email", "name")), Pagination(1, 1, 1L)), result)
    }

    @Test
    fun shouldLogin() {
        val loginRequest = LoginRequest("email", "password")
        val loginResponse = LoginResponse("token", 1, ZonedDateTime.now())
        whenever(loginCommand.execute(loginRequest)).thenReturn(loginResponse)

        val result = userFacade.login(loginRequest)

        Assertions.assertEquals(loginResponse, result)
    }

    @Test
    fun shouldLoginWithFacebook() {
        val loginDto = LoginWithFacebookRequest("token")
        val loginResponse = LoginResponse("token", 1, ZonedDateTime.now())
        whenever(loginWithFacebookCommand.execute(loginDto)).thenReturn(loginResponse)

        val result = userFacade.login(loginDto)

        Assertions.assertEquals(loginResponse, result)
    }

    @Test
    fun shouldRegister() {
        val registerRequest = RegisterRequest("email", "name", "password")
        val loginResponse = LoginResponse("token", 1, ZonedDateTime.now())
        whenever(registerCommand.execute(registerRequest)).thenReturn(loginResponse)

        val result = userFacade.register(registerRequest)

        Assertions.assertEquals(loginResponse, result)
    }

    @Test
    fun shouldRefresh() {
        val refreshTokenResponse = RefreshTokenResponse("token", ZonedDateTime.now())
        whenever(refreshTokenCommand.execute()).thenReturn(refreshTokenResponse)

        val result = userFacade.refresh()

        Assertions.assertEquals(refreshTokenResponse, result)
    }

    @Test
    fun shouldReturnLoggedUser() {
        whenever(getLoggedUserQuery.execute()).thenReturn(UserResponse(UserDto(1, "email", "name")))

        val result = userFacade.getLoggedUser()

        Assertions.assertEquals(UserResponse(UserDto(1, "email", "name")), result)
    }
}
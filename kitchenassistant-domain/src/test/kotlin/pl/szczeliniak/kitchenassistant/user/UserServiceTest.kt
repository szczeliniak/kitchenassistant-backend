package pl.szczeliniak.kitchenassistant.user

import org.assertj.core.util.Sets
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination
import pl.szczeliniak.kitchenassistant.user.commands.dto.*
import pl.szczeliniak.kitchenassistant.user.dto.request.*
import pl.szczeliniak.kitchenassistant.user.dto.response.*
import java.time.ZonedDateTime

internal class UserServiceTest : JunitBaseClass() {

    @InjectMocks
    private lateinit var userService: UserService

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
        whenever(getUserByIdQuery.execute(1)).thenReturn(UserResponse(UserDto(1, "email")))

        val result = userService.findById(1)

        Assertions.assertEquals(UserResponse(UserDto(1, "email")), result)
    }

    @Test
    fun shouldReturnUsers() {
        whenever(getUsersQuery.execute(1L, 1)).thenReturn(
            UsersResponse(
                Sets.newLinkedHashSet(UserDto(1, "email")),
                Pagination(1, 1, 1L)
            )
        )

        val result = userService.findAll(1L, 1)

        Assertions.assertEquals(UsersResponse(Sets.newLinkedHashSet(UserDto(1, "email")), Pagination(1, 1, 1L)), result)
    }

    @Test
    fun shouldLogin() {
        val loginRequest = LoginRequest("email", "password")
        val loginResponse = LoginResponse("token", 1, ZonedDateTime.now())
        whenever(loginCommand.execute(loginRequest)).thenReturn(loginResponse)

        val result = userService.login(loginRequest)

        Assertions.assertEquals(loginResponse, result)
    }

    @Test
    fun shouldLoginWithFacebook() {
        val loginDto = LoginWithFacebookRequest("token")
        val loginResponse = LoginResponse("token", 1, ZonedDateTime.now())
        whenever(loginWithFacebookCommand.execute(loginDto)).thenReturn(loginResponse)

        val result = userService.login(loginDto)

        Assertions.assertEquals(loginResponse, result)
    }

    @Test
    fun shouldRegister() {
        val registerRequest = RegisterRequest("email", "password")
        val loginResponse = LoginResponse("token", 1, ZonedDateTime.now())
        whenever(registerCommand.execute(registerRequest)).thenReturn(loginResponse)

        val result = userService.register(registerRequest)

        Assertions.assertEquals(loginResponse, result)
    }

    @Test
    fun shouldRefresh() {
        val refreshTokenResponse = RefreshTokenResponse("token", ZonedDateTime.now())
        whenever(refreshTokenCommand.execute()).thenReturn(refreshTokenResponse)

        val result = userService.refresh()

        Assertions.assertEquals(refreshTokenResponse, result)
    }

    @Test
    fun shouldReturnLoggedUser() {
        whenever(getLoggedUserQuery.execute()).thenReturn(UserResponse(UserDto(1, "email")))

        val result = userService.getLoggedUser()

        Assertions.assertEquals(UserResponse(UserDto(1, "email")), result)
    }
}
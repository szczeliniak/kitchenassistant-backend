package pl.szczeliniak.kitchenassistant.user

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.dtos.Page
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.db.UserCriteria
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import pl.szczeliniak.kitchenassistant.user.dto.request.LoginRequest
import pl.szczeliniak.kitchenassistant.user.dto.request.LoginWithFacebookRequest
import pl.szczeliniak.kitchenassistant.user.dto.request.RegisterRequest
import pl.szczeliniak.kitchenassistant.user.dto.response.*
import java.time.ZonedDateTime
import java.util.*

internal class UserServiceTest : JunitBaseClass() {

    @InjectMocks
    private lateinit var userService: UserService

    @Mock
    private lateinit var requestContext: RequestContext

    @Mock
    private lateinit var userDao: UserDao

    @Mock
    private lateinit var userMapper: UserMapper

    @Mock
    private lateinit var userFactory: UserFactory

    @Mock
    private lateinit var tokenFactory: TokenFactory

    @Mock
    private lateinit var passwordMatcher: PasswordMatcher

    @Mock
    private lateinit var facebookConnector: FacebookConnector

    @Test
    fun shouldReturnUser() {
        val user = user()
        val userDto = userDetailsDto()
        whenever(userDao.findById(1)).thenReturn(user)
        whenever(userMapper.mapDetails(user)).thenReturn(userDto)

        val result = userService.findById(1)

        assertThat(result).isEqualTo(UserResponse(userDto))
    }

    @Test
    fun shouldReturnUsers() {
        val user = user()
        val userDto = userDto()
        val criteria = UserCriteria()
        whenever(userDao.findAll(criteria, 100, 25)).thenReturn(setOf(user))
        whenever(userDao.count(criteria)).thenReturn(280)
        whenever(userMapper.map(user)).thenReturn(userDto)

        val result = userService.findAll(5, 25)

        assertThat(result).isEqualTo(UsersResponse(Page(5, 25, 12, setOf(userDto))))
    }

    @Test
    fun shouldReturnLoggedUser() {
        val user = user()
        val userDto = userDetailsDto()
        whenever(requestContext.userId()).thenReturn(1)
        whenever(userDao.findById(1)).thenReturn(user)
        whenever(userMapper.mapDetails(user)).thenReturn(userDto)

        val result = userService.getLoggedUser()

        assertThat(result).isEqualTo(UserResponse(userDto))
    }

    @Test
    fun shouldRegister() {
        val user = user()
        val request = RegisterRequest("email", "password")
        val tokenValidity = ZonedDateTime.now()
        whenever(userDao.findAll(UserCriteria("email"), 0, 1)).thenReturn(emptySet())
        whenever(userFactory.create(request)).thenReturn(user)
        whenever(userDao.save(user)).thenReturn(user(1))
        whenever(tokenFactory.create(1, "email")).thenReturn(TokenFactory.Token("token", "email", tokenValidity))

        val result = userService.register(request)

        assertThat(result).isEqualTo(LoginResponse("token", 1, "email", tokenValidity))
    }

    @Test
    fun shouldThrowExceptionWhenRegisterAndUserExists() {
        whenever(userDao.findAll(UserCriteria("email"), 0, 1)).thenReturn(setOf(user()))

        assertThatThrownBy { userService.register(RegisterRequest("email", "password")) }.isInstanceOf(
            KitchenAssistantException::class.java
        ).hasMessage("User with email already exists")

    }

    @Test
    fun shouldRefreshToken() {
        val user = user(1)
        val tokenValidity = ZonedDateTime.now()
        whenever(requestContext.requireUserId()).thenReturn(1)
        whenever(userDao.findById(1)).thenReturn(user)
        whenever(tokenFactory.create(1, "email")).thenReturn(TokenFactory.Token("token", "email", tokenValidity))

        val result = userService.refresh()

        assertThat(result).isEqualTo(RefreshTokenResponse("token", "email", tokenValidity))
    }

    @Test
    fun shouldThrowExceptionWhenRefreshAndUserNotFound() {
        whenever(requestContext.requireUserId()).thenReturn(1)
        whenever(userDao.findById(1)).thenReturn(null)

        assertThatThrownBy { userService.refresh() }.isInstanceOf(KitchenAssistantException::class.java)
            .hasMessage("User not found")

    }

    @Test
    fun shouldLogin() {
        val user = user(1)
        val tokenValidity = ZonedDateTime.now()

        whenever(userDao.findAll(UserCriteria("email"), 0, 1)).thenReturn(setOf(user))
        whenever(passwordMatcher.matches("pass", "password")).thenReturn(true)
        whenever(tokenFactory.create(1, "email")).thenReturn(TokenFactory.Token("token", "email", tokenValidity))

        val result = userService.login(LoginRequest("email", "password"))

        assertThat(result).isEqualTo(LoginResponse("token", 1, "email", tokenValidity))
    }

    @Test
    fun shouldThrowExceptionWhenLoginAndUserNotFound() {
        whenever(userDao.findAll(UserCriteria("email"), 0, 1)).thenReturn(emptySet())

        assertThatThrownBy { userService.login(LoginRequest("email", "password")) }.isInstanceOf(
            KitchenAssistantException::class.java
        )
            .hasMessage("User not found")

    }

    @Test
    fun shouldThrowExceptionWhenLoginAndPasswordsDoNotMatch() {
        val user = user(1)
        whenever(userDao.findAll(UserCriteria("email"), 0, 1)).thenReturn(setOf(user))
        whenever(passwordMatcher.matches("pass", "password")).thenReturn(false)

        assertThatThrownBy { userService.login(LoginRequest("email", "password")) }.isInstanceOf(
            KitchenAssistantException::class.java
        )
            .hasMessage("Passwords do not match")

    }

    @Test
    fun shouldLoginWithFacebookWhenUserAlreadyExists() {
        val tokenValidity = ZonedDateTime.now()

        whenever(facebookConnector.login("token")).thenReturn(facebookLoginResponse())
        whenever(userDao.findAll(UserCriteria("email"), 0, 1)).thenReturn(setOf(user(1)))
        whenever(tokenFactory.create(1, "email")).thenReturn(TokenFactory.Token("token", "email", tokenValidity))

        val result = userService.login(LoginWithFacebookRequest("token"))

        assertThat(result).isEqualTo(LoginResponse("token", 1, "email", tokenValidity))
    }

    @Test
    fun shouldLoginWithFacebookWhenUserDoesNotExist() {
        val user = user(1)
        val tokenValidity = ZonedDateTime.now()

        whenever(facebookConnector.login("token")).thenReturn(facebookLoginResponse())
        whenever(userDao.findAll(UserCriteria("email"), 0, 1)).thenReturn(Collections.emptySet())
        whenever(userFactory.create("email", "")).thenReturn(user)
        whenever(userDao.save(user)).thenReturn(user)
        whenever(tokenFactory.create(1, "email")).thenReturn(TokenFactory.Token("token", "email", tokenValidity))

        val result = userService.login(LoginWithFacebookRequest("token"))

        assertThat(result).isEqualTo(LoginResponse("token", 1, "email", tokenValidity))
    }

    @Test
    fun shouldThrowExceptionWhenFacebookReturnsNull() {
        whenever(facebookConnector.login("token")).thenReturn(null)

        assertThatThrownBy { userService.login(LoginWithFacebookRequest("token")) }
            .isInstanceOf(KitchenAssistantException::class.java)
            .hasMessage("Cannot login with Facebook")
    }

    private fun facebookLoginResponse(): FacebookLoginResponse {
        return FacebookLoginResponse("id", "name", "email")
    }

    private fun userDetailsDto(): UserResponse.UserDto {
        return UserResponse.UserDto(0, "")
    }

    private fun userDto(): UsersResponse.UserDto {
        return UsersResponse.UserDto(0, "")
    }

    private fun user(id: Int = 0): User {
        return User(id, "email", "pass")
    }

}
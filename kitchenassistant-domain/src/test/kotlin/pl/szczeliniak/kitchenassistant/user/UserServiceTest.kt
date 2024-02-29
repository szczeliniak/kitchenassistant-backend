package pl.szczeliniak.kitchenassistant.user

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.db.UserCriteria
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import pl.szczeliniak.kitchenassistant.user.dto.request.LoginRequest
import pl.szczeliniak.kitchenassistant.user.dto.request.LoginWithFacebookRequest
import pl.szczeliniak.kitchenassistant.user.dto.request.RegisterRequest
import pl.szczeliniak.kitchenassistant.user.dto.response.FacebookLoginResponse
import pl.szczeliniak.kitchenassistant.user.dto.response.LoginResponse

class UserServiceTest {

    private val requestContext: RequestContext = mockk();
    private val userDao: UserDao = mockk();
    private val passwordEncoder: PasswordEncoder = mockk();
    private val tokenFactory: TokenFactory = mockk();
    private val passwordMatcher: PasswordMatcher = mockk();
    private val facebookConnector: FacebookConnector = mockk();

    private var userService: UserService = UserService(userDao, passwordMatcher, tokenFactory, facebookConnector, requestContext, passwordEncoder)

    @Test
    fun shouldRegister() {
        val request = RegisterRequest("test@test.pl", "password")
        every { userDao.findAll(UserCriteria("test@test.pl"), 0, 1) } returns emptyList()
        every { passwordEncoder.encode("password") } returns "encodedPassword"
        every { userDao.save(any()) } returns user(1)
        every { tokenFactory.create(1, "test@test.pl", TokenType.ACCESS) } returns "accessToken"
        every { tokenFactory.create(1, "test@test.pl", TokenType.REFRESH) } returns "refreshToken"

        val result = userService.register(request)

        assertThat(result).isEqualTo(LoginResponse("accessToken", "refreshToken"))
    }

    @Test
    fun shouldThrowExceptionWhenRegisterAndUserExists() {
        every { userDao.findAll(UserCriteria("test@test.pl"), 0, 1) } returns listOf(user())

        assertThatThrownBy { userService.register(RegisterRequest("test@test.pl", "password")) }
            .isInstanceOf(KitchenAssistantException::class.java)
            .hasMessage("User with email already exists")
    }

    @Test
    fun shouldRefreshToken() {
        every { requestContext.tokenType() } returns TokenType.REFRESH
        every { requestContext.userId() } returns 1
        every { userDao.findById(1) } returns user(1)
        every { tokenFactory.create(1, "test@test.pl", TokenType.ACCESS) } returns "accessToken"
        every { tokenFactory.create(1, "test@test.pl", TokenType.REFRESH) } returns "refreshToken"

        val result = userService.refresh()

        assertThat(result).isEqualTo(LoginResponse("accessToken", "refreshToken"))
    }

    @Test
    fun shouldThrowExceptionWhenRefreshAndUserNotFound() {
        every { requestContext.userId() } returns 1
        every { userDao.findById(1) } returns null
        every { requestContext.tokenType() } returns TokenType.REFRESH

        assertThatThrownBy { userService.refresh() }
            .isInstanceOf(KitchenAssistantException::class.java)
            .hasMessage("User not found")
    }

    @Test
    fun shouldThrowExceptionWhenRefreshAndTokenIsWrong() {
        every { requestContext.tokenType() } returns TokenType.ACCESS

        assertThatThrownBy { userService.refresh() }
            .isInstanceOf(KitchenAssistantException::class.java)
            .hasMessage("Wrong token type")
    }

    @Test
    fun shouldLogin() {
        every { userDao.findAll(UserCriteria("test@test.pl"), 0, 1) } returns listOf(user(1))
        every { passwordMatcher.matches("pass", "password") } returns true
        every { tokenFactory.create(1, "test@test.pl", TokenType.ACCESS) } returns "accessToken"
        every { tokenFactory.create(1, "test@test.pl", TokenType.REFRESH) } returns "refreshToken"

        val result = userService.login(LoginRequest("test@test.pl", "password"))

        assertThat(result).isEqualTo(LoginResponse("accessToken", "refreshToken"))
    }

    @Test
    fun shouldThrowExceptionWhenLoginAndUserNotFound() {
        every { userDao.findAll(UserCriteria("test@test.pl"), 0, 1) } returns emptyList()

        assertThatThrownBy { userService.login(LoginRequest("test@test.pl", "password")) }
            .isInstanceOf(KitchenAssistantException::class.java)
            .hasMessage("User not found")
    }

    @Test
    fun shouldThrowExceptionWhenLoginAndPasswordsDoNotMatch() {
        every { userDao.findAll(UserCriteria("test@test.pl"), 0, 1) } returns listOf(user(1))
        every { passwordMatcher.matches("pass", "password") } returns false

        assertThatThrownBy { userService.login(LoginRequest("test@test.pl", "password")) }
            .isInstanceOf(KitchenAssistantException::class.java)
            .hasMessage("Passwords do not match")
    }

    @Test
    fun shouldLoginWithFacebookWhenUserAlreadyExists() {
        every { facebookConnector.login("token") } returns facebookLoginResponse()
        every { userDao.findAll(UserCriteria("test@test.pl"), 0, 1) } returns listOf(user(1))
        every { tokenFactory.create(1, "test@test.pl", TokenType.ACCESS) } returns "accessToken"
        every { tokenFactory.create(1, "test@test.pl", TokenType.REFRESH) } returns "refreshToken"

        val result = userService.login(LoginWithFacebookRequest("token"))

        assertThat(result).isEqualTo(LoginResponse("accessToken", "refreshToken"))
    }

    @Test
    fun shouldLoginWithFacebookWhenUserDoesNotExist() {
        every { facebookConnector.login("token") } returns facebookLoginResponse()
        every { userDao.findAll(UserCriteria("test@test.pl"), 0, 1) } returns emptyList()
        every { passwordEncoder.encode("") } returns "encodedPassword"
        every { userDao.save(any()) } returns user(1)
        every { tokenFactory.create(1, "test@test.pl", TokenType.ACCESS) } returns "accessToken"
        every { tokenFactory.create(1, "test@test.pl", TokenType.REFRESH) } returns "refreshToken"

        val result = userService.login(LoginWithFacebookRequest("token"))

        assertThat(result).isEqualTo(LoginResponse("accessToken", "refreshToken"))
    }

    @Test
    fun shouldThrowExceptionWhenFacebookReturnsNull() {
        every { facebookConnector.login("token") } returns null

        assertThatThrownBy { userService.login(LoginWithFacebookRequest("token")) }
            .isInstanceOf(KitchenAssistantException::class.java)
            .hasMessage("Cannot login with Facebook")
    }

    private fun facebookLoginResponse(): FacebookLoginResponse {
        return FacebookLoginResponse("id", "name", "test@test.pl")
    }

    private fun user(id: Int = 0): User {
        return User(id, "test@test.pl", "pass")
    }

}
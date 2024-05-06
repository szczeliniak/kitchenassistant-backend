package pl.szczeliniak.cookbook.user

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import pl.szczeliniak.cookbook.shared.CookBookException
import pl.szczeliniak.cookbook.shared.RequestContext
import pl.szczeliniak.cookbook.shared.TokenType
import pl.szczeliniak.cookbook.shared.dtos.SuccessResponse
import pl.szczeliniak.cookbook.user.db.User
import pl.szczeliniak.cookbook.user.db.UserCriteria
import pl.szczeliniak.cookbook.user.db.UserDao
import pl.szczeliniak.cookbook.user.dto.request.LoginRequest
import pl.szczeliniak.cookbook.user.dto.request.LoginWithFacebookRequest
import pl.szczeliniak.cookbook.user.dto.request.RegisterRequest
import pl.szczeliniak.cookbook.user.dto.request.ResetPasswordRequest
import pl.szczeliniak.cookbook.user.dto.request.UpdatePasswordRequest
import pl.szczeliniak.cookbook.user.dto.response.FacebookLoginResponse
import pl.szczeliniak.cookbook.user.dto.response.LoginResponse
import java.util.*

class UserServiceTest {

    private val requestContext: RequestContext = mockk()
    private val userDao: UserDao = mockk()
    private val passwordEncoder: PasswordEncoder = mockk()
    private val tokenFactory: TokenFactory = mockk()
    private val passwordMatcher: PasswordMatcher = mockk()
    private val facebookConnector: FacebookConnector = mockk()
    private val passwordGenerator: PasswordGenerator = mockk()
    private val mailService: MailService = mockk()

    private var userService: UserService = UserService(userDao, passwordMatcher, tokenFactory, facebookConnector, requestContext, passwordEncoder, passwordGenerator, mailService)

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
            .isInstanceOf(CookBookException::class.java)
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
            .isInstanceOf(CookBookException::class.java)
            .hasMessage("User not found")
    }

    @Test
    fun shouldThrowExceptionWhenRefreshAndTokenIsWrong() {
        every { requestContext.tokenType() } returns TokenType.ACCESS

        assertThatThrownBy { userService.refresh() }
            .isInstanceOf(CookBookException::class.java)
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
            .isInstanceOf(CookBookException::class.java)
            .hasMessage("User not found")
    }

    @Test
    fun shouldThrowExceptionWhenLoginAndPasswordsDoNotMatch() {
        every { userDao.findAll(UserCriteria("test@test.pl"), 0, 1) } returns listOf(user(1))
        every { passwordMatcher.matches("pass", "password") } returns false

        assertThatThrownBy { userService.login(LoginRequest("test@test.pl", "password")) }
            .isInstanceOf(CookBookException::class.java)
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
            .isInstanceOf(CookBookException::class.java)
            .hasMessage("Cannot login with Facebook")
    }

    @Test
    fun shouldResetPassword() {
        val user = user(1)
        every { userDao.findAll(UserCriteria("email"), 0, 1) } returns Collections.singletonList(user)
        every { passwordGenerator.generate() } returns "newPassword"
        every { passwordEncoder.encode("newPassword") } returns "encodedPassword"
        every { userDao.save(user) } returns user
        every {
            mailService.send(
                "test@test.pl",
                "Your password has been reset!",
                "Hi!<br/>Your password has been reset, so your current credentials for logging are:<br/>email: <b>test@test.pl</b>,<br/>password: <b>newPassword</b>.<br/><br/>You can change the password right after you log in."
            )
        } returns Unit

        val response = userService.resetPassword(ResetPasswordRequest("email"))

        assertThat(response).isEqualTo(SuccessResponse(1))
        verify { userDao.save(user) }
        verify {
            mailService.send(
                "test@test.pl",
                "Your password has been reset!",
                "Hi!<br/>Your password has been reset, so your current credentials for logging are:<br/>email: <b>test@test.pl</b>,<br/>password: <b>newPassword</b>.<br/><br/>You can change the password right after you log in."
            )
        }
    }

    @Test
    fun shouldThrowExceptionWhenResetPasswordAndUserNotFound() {
        every { userDao.findAll(UserCriteria("email"), 0, 1) } returns Collections.emptyList()

        assertThatThrownBy { userService.resetPassword(ResetPasswordRequest("email")) }
            .isInstanceOf(CookBookException::class.java)
            .hasMessage("User not found")
    }

    @Test
    fun shouldUpdatePassword() {
        val user = user(1)
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 1
        every { userDao.findById(1) } returns user
        every { passwordMatcher.matches("pass", "oldPassword") } returns true
        every { passwordEncoder.encode("newPassword") } returns "encodedPassword"
        every { userDao.save(user) } returns user

        val response = userService.updatePassword(UpdatePasswordRequest("oldPassword", "newPassword"))

        assertThat(response).isEqualTo(SuccessResponse(1))
        verify { userDao.save(user) }
    }

    @Test
    fun shouldThrowExceptionWhenUpdatePasswordAndPasswordDoesNotMatch() {
        val user = user(1)
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 1
        every { userDao.findById(1) } returns user
        every { passwordMatcher.matches("pass", "oldPassword") } returns false

        assertThatThrownBy { userService.updatePassword(UpdatePasswordRequest("oldPassword", "newPassword")) }
            .isInstanceOf(CookBookException::class.java)
            .hasMessage("Passwords do not match")
    }

    @Test
    fun shouldThrowExceptionWhenUpdatePasswordAndUserNotFound() {
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 1
        every { userDao.findById(1) } returns null

        assertThatThrownBy { userService.updatePassword(UpdatePasswordRequest("oldPassword", "newPassword")) }
            .isInstanceOf(CookBookException::class.java)
            .hasMessage("User not found")
    }

    private fun facebookLoginResponse(): FacebookLoginResponse {
        return FacebookLoginResponse("id", "name", "test@test.pl")
    }

    private fun user(id: Int = 0): User {
        return User(id, "test@test.pl", "pass")
    }

}
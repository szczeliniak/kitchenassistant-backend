package pl.szczeliniak.kitchenassistant.user

import pl.szczeliniak.kitchenassistant.shared.BaseService
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.db.UserCriteria
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import pl.szczeliniak.kitchenassistant.user.dto.request.LoginRequest
import pl.szczeliniak.kitchenassistant.user.dto.request.LoginWithFacebookRequest
import pl.szczeliniak.kitchenassistant.user.dto.request.RegisterRequest
import pl.szczeliniak.kitchenassistant.user.dto.response.LoginResponse

open class UserService(
    private val userDao: UserDao,
    private val passwordMatcher: PasswordMatcher,
    private val tokenFactory: TokenFactory,
    private val facebookConnector: FacebookConnector,
    requestContext: RequestContext,
    private val passwordEncoder: PasswordEncoder
) : BaseService(requestContext) {

    companion object {
        private val EMAIL_PATTERN = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
    }

    open fun login(request: LoginRequest): LoginResponse {
        if (!request.email.matches(EMAIL_PATTERN)) {
            throw KitchenAssistantException(ErrorCode.INVALID_EMAIL)
        }
        val user = userDao.findAll(UserCriteria(request.email), 0, 1).firstOrNull() ?: throw KitchenAssistantException(
            ErrorCode.USER_NOT_FOUND
        )

        if (user.password.let { !passwordMatcher.matches(it, request.password) }) {
            throw KitchenAssistantException(ErrorCode.PASSWORDS_DO_NOT_MATCH)
        }
        return LoginResponse(
            tokenFactory.create(user.id, user.email, TokenType.ACCESS),
            tokenFactory.create(user.id, user.email, TokenType.REFRESH)
        )
    }

    open fun login(request: LoginWithFacebookRequest): LoginResponse {
        val user = facebookConnector.login(request.token)?.let {
            userDao.findAll(UserCriteria(it.email), 0, 1).firstOrNull() ?: userDao.save(
                createUser(it.email, "")
            )
        } ?: throw KitchenAssistantException(ErrorCode.CANNOT_LOGIN_WITH_FACEBOOK)

        return LoginResponse(
            tokenFactory.create(user.id, user.email, TokenType.ACCESS),
            tokenFactory.create(user.id, user.email, TokenType.REFRESH)
        )
    }

    open fun register(request: RegisterRequest): LoginResponse {
        if (!request.email.matches(EMAIL_PATTERN)) {
            throw KitchenAssistantException(ErrorCode.INVALID_EMAIL)
        }
        if (userDao.findAll(UserCriteria(request.email), 0, 1).firstOrNull() != null) {
            throw KitchenAssistantException(ErrorCode.USER_ALREADY_EXISTS)
        }

        val user = userDao.save(createUser(request.email, request.password))
        return LoginResponse(
            tokenFactory.create(user.id, user.email, TokenType.ACCESS),
            tokenFactory.create(user.id, user.email, TokenType.REFRESH)
        )
    }

    open fun refresh(): LoginResponse {
        requireTokenType(TokenType.REFRESH)
        val user = userDao.findById(requestContext.userId())
            ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND)
        return LoginResponse(
            tokenFactory.create(user.id, user.email, TokenType.ACCESS),
            tokenFactory.create(user.id, user.email, TokenType.REFRESH)
        )
    }

    private fun createUser(email: String, password: String): User {
        return User(0, email, password.let { passwordEncoder.encode(it) })
    }

}
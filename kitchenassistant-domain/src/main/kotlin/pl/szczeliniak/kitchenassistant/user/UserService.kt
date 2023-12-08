package pl.szczeliniak.kitchenassistant.user

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.PaginationUtils
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.dtos.Page
import pl.szczeliniak.kitchenassistant.user.db.UserCriteria
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import pl.szczeliniak.kitchenassistant.user.dto.request.LoginRequest
import pl.szczeliniak.kitchenassistant.user.dto.request.LoginWithFacebookRequest
import pl.szczeliniak.kitchenassistant.user.dto.request.RegisterRequest
import pl.szczeliniak.kitchenassistant.user.dto.response.LoginResponse
import pl.szczeliniak.kitchenassistant.user.dto.response.RefreshTokenResponse
import pl.szczeliniak.kitchenassistant.user.dto.response.UserResponse
import pl.szczeliniak.kitchenassistant.user.dto.response.UsersResponse

open class UserService(
    private val userMapper: UserMapper,
    private val userDao: UserDao,
    private val passwordMatcher: PasswordMatcher,
    private val tokenFactory: TokenFactory,
    private val facebookConnector: FacebookConnector,
    private val userFactory: UserFactory,
    private val requestContext: RequestContext
) {

    open fun findById(id: Int): UserResponse {
        return UserResponse(
            userMapper.mapDetails(
                userDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND)
            )
        )
    }

    open fun findAll(page: Long?, limit: Int?): UsersResponse {
        val currentPage = PaginationUtils.calculatePageNumber(page)
        val currentLimit = PaginationUtils.calculateLimit(limit)
        val offset = PaginationUtils.calculateOffset(currentPage, currentLimit)
        val criteria = UserCriteria()
        val totalNumberOfPages = PaginationUtils.calculateNumberOfPages(currentLimit, userDao.count(criteria))
        return UsersResponse(
            Page(
                currentPage,
                currentLimit,
                totalNumberOfPages,
                userDao.findAll(criteria, offset, currentLimit).map { userMapper.map(it) }.toSet()
            )
        )
    }

    open fun login(request: LoginRequest): LoginResponse {
        val user = userDao.findAll(UserCriteria(request.email), 0, 1).firstOrNull() ?: throw KitchenAssistantException(
            ErrorCode.USER_NOT_FOUND
        )

        if (user.password?.let { !passwordMatcher.matches(it, request.password) } == true) {
            throw KitchenAssistantException(ErrorCode.PASSWORDS_DO_NOT_MATCH)
        }
        val token = tokenFactory.create(user.id, user.email)
        return LoginResponse(token.token, user.id, user.email, token.validTo)
    }

    open fun login(request: LoginWithFacebookRequest): LoginResponse {
        val user = facebookConnector.login(request.token)?.let {
            userDao.findAll(UserCriteria(it.email!!), 0, 1).firstOrNull() ?: userDao.save(
                userFactory.create(it.email, "")
            )
        } ?: throw KitchenAssistantException(ErrorCode.CANNOT_LOGIN_WITH_FACEBOOK)

        val token = tokenFactory.create(user.id, user.email)
        return LoginResponse(token.token, user.id, user.email, token.validTo)
    }

    open fun register(request: RegisterRequest): LoginResponse {
        if (userDao.findAll(UserCriteria(request.email), 0, 1).firstOrNull() != null) {
            throw KitchenAssistantException(ErrorCode.USER_ALREADY_EXISTS)
        }

        val user = userDao.save(userFactory.create(request))
        val token = tokenFactory.create(user.id, user.email)
        return LoginResponse(token.token, user.id, user.email, token.validTo)
    }

    open fun refresh(): RefreshTokenResponse {
        val user =
            userDao.findById(requestContext.requireUserId())
                ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND)
        val token = tokenFactory.create(user.id, user.email)
        return RefreshTokenResponse(token.token, token.email, token.validTo)
    }

    open fun getLoggedUser(): UserResponse {
        return UserResponse(
            userMapper.mapDetails(
                userDao.findById(
                    requestContext.userId() ?: throw KitchenAssistantException(ErrorCode.MISSING_USER_ID)
                ) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND)
            )
        )
    }

}
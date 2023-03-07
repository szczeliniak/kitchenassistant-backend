package pl.szczeliniak.kitchenassistant.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.user.commands.*
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.GetLoggedUserQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUsersQuery
import pl.szczeliniak.kitchenassistant.user.queries.UserConverter

@Configuration
class UserConfiguration {

    @Bean
    fun userConverter(): UserConverter = UserConverter()

    @Bean
    fun getUserByIdQuery(userDao: UserDao, userConverter: UserConverter): GetUserByIdQuery =
            GetUserByIdQuery(userDao, userConverter)

    @Bean
    fun userFacade(
            userDao: UserDao,
            requestContext: RequestContext,
            passwordEncoder: PasswordEncoder,
            passwordMatcher: PasswordMatcher,
            tokenFactory: TokenFactory,
            facebookConnector: FacebookConnector,
            userConverter: UserConverter,
            getUserByIdQuery: GetUserByIdQuery
    ): UserFacade {
        val userFactory = UserFactory(passwordEncoder)
        return UserFacade(
                getUserByIdQuery,
                GetLoggedUserQuery(userDao, requestContext, userConverter),
                GetUsersQuery(userDao, userConverter),
                LoginCommand(userDao, passwordMatcher, tokenFactory),
                LoginWithFacebookCommand(userDao, tokenFactory, facebookConnector, userFactory),
                RegisterCommand(userFactory, tokenFactory, userDao),
                RefreshTokenCommand(tokenFactory, requestContext, userDao)
        )
    }

    @Bean
    fun passwordEncoder(): org.springframework.security.crypto.password.PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}
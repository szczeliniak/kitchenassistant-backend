package pl.szczeliniak.kitchenassistant.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.user.commands.*
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory
import pl.szczeliniak.kitchenassistant.user.queries.GetLoggedUserQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUsersQuery
import pl.szczeliniak.kitchenassistant.user.queries.UserConverter

@Configuration
class UserConfiguration {

    @Bean
    fun userFacade(
        userDao: UserDao,
        requestContext: RequestContext,
        passwordEncoder: PasswordEncoder,
        passwordMatcher: PasswordMatcher,
        tokenFactory: TokenFactory,
        facebookConnector: FacebookConnector
    ): UserFacade {
        val userConverter = UserConverter()
        val userFactory = UserFactory(passwordEncoder)
        return UserFacade(
            GetUserByIdQuery(userDao, userConverter),
            GetLoggedUserQuery(userDao, requestContext, userConverter),
            GetUsersQuery(userDao, userConverter),
            AddUserCommand(userDao, userFactory),
            LoginCommand(userDao, passwordMatcher, tokenFactory),
            LoginWithFacebookCommand(userDao, tokenFactory, facebookConnector, userFactory),
            RegisterCommand(userFactory, tokenFactory, userDao),
            RefreshTokenCommand(tokenFactory, requestContext, userDao)
        )
    }

}
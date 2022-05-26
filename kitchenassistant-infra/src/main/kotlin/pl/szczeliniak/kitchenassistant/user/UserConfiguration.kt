package pl.szczeliniak.kitchenassistant.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.user.commands.AddUserCommand
import pl.szczeliniak.kitchenassistant.user.commands.LoginCommand
import pl.szczeliniak.kitchenassistant.user.commands.RefreshTokenCommand
import pl.szczeliniak.kitchenassistant.user.commands.RegisterCommand
import pl.szczeliniak.kitchenassistant.user.commands.factories.TokenFactory
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory
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
    fun getUsersQuery(userDao: UserDao, userConverter: UserConverter): GetUsersQuery =
        GetUsersQuery(userDao, userConverter)

    @Bean
    fun addUserCommand(userDao: UserDao, userFactory: UserFactory): AddUserCommand =
        AddUserCommand(userDao, userFactory)

    @Bean
    fun userFactory(passwordEncoder: PasswordEncoder): UserFactory = UserFactory(passwordEncoder)

    @Bean
    fun loginCommand(
        userDao: UserDao,
        passwordMatcher: PasswordMatcher,
        tokenFactory: TokenFactory
    ): LoginCommand = LoginCommand(userDao, passwordMatcher, tokenFactory)

    @Bean
    fun registerCommand(addUserCommand: AddUserCommand, tokenFactory: TokenFactory): RegisterCommand =
        RegisterCommand(addUserCommand, tokenFactory)

    @Bean
    fun refreshTokenCommand(
        tokenFactory: TokenFactory,
        requestContext: RequestContext,
        getUserByIdQuery: GetUserByIdQuery
    ): RefreshTokenCommand =
        RefreshTokenCommand(tokenFactory, requestContext, getUserByIdQuery)

    @Bean
    fun passwordEncoder(): org.springframework.security.crypto.password.PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}
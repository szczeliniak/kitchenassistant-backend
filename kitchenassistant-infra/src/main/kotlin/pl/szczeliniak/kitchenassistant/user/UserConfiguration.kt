package pl.szczeliniak.kitchenassistant.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.user.commands.AddNewUserCommand
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory
import pl.szczeliniak.kitchenassistant.user.queries.GetUserQuery
import pl.szczeliniak.kitchenassistant.user.queries.GetUsersQuery

@Configuration
class UserConfiguration {

    @Bean
    fun getUserQuery(userDao: UserDao): GetUserQuery = GetUserQuery(userDao)

    @Bean
    fun getUsersQuery(userDao: UserDao): GetUsersQuery = GetUsersQuery(userDao)

    @Bean
    fun addNewUserCommand(userDao: UserDao, userFactory: UserFactory): AddNewUserCommand =
        AddNewUserCommand(userDao, userFactory)

    @Bean
    fun userFactory(): UserFactory = UserFactory()

}
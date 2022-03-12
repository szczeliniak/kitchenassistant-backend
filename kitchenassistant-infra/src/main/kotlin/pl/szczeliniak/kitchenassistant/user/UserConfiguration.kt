package pl.szczeliniak.kitchenassistant.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.user.commands.AddNewUser
import pl.szczeliniak.kitchenassistant.user.commands.factories.UserFactory
import pl.szczeliniak.kitchenassistant.user.queries.GetUser
import pl.szczeliniak.kitchenassistant.user.queries.GetUsers

@Configuration
class UserConfiguration {

    @Bean
    fun getUser(userDao: UserDao): GetUser = GetUser(userDao)

    @Bean
    fun getUsers(userDao: UserDao): GetUsers = GetUsers(userDao)

    @Bean
    fun addNewUser(userDao: UserDao, userFactory: UserFactory): AddNewUser = AddNewUser(userDao, userFactory)

    @Bean
    fun userFactory(): UserFactory = UserFactory()

}
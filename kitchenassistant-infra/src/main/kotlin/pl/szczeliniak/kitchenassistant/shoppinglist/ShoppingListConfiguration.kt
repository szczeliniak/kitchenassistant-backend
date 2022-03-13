package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.AddShoppingListCommand
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.AddShoppingListItemCommand
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.DeleteShoppingListCommand
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.DeleteShoppingListItemCommand
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListFactory
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListItemFactory
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListsQuery

@Configuration
class ShoppingListConfiguration {

    @Bean
    fun shoppingListItemFactory(): ShoppingListItemFactory = ShoppingListItemFactory()

    @Bean
    fun shoppingListFactory(shoppingListItemFactory: ShoppingListItemFactory): ShoppingListFactory =
        ShoppingListFactory(shoppingListItemFactory)

    @Bean
    fun addShoppingListCommand(shoppingListDao: ShoppingListDao, shoppingListFactory: ShoppingListFactory) =
        AddShoppingListCommand(shoppingListDao, shoppingListFactory)

    @Bean
    fun addShoppingListItemCommand(shoppingListDao: ShoppingListDao, shoppingListItemFactory: ShoppingListItemFactory) =
        AddShoppingListItemCommand(shoppingListDao, shoppingListItemFactory)

    @Bean
    fun deleteShoppingListCommand(shoppingListDao: ShoppingListDao) = DeleteShoppingListCommand(shoppingListDao)

    @Bean
    fun deleteShoppingListItemCommand(shoppingListDao: ShoppingListDao) = DeleteShoppingListItemCommand(shoppingListDao)

    @Bean
    fun getShoppingListQuery(shoppingListDao: ShoppingListDao) = GetShoppingListQuery(shoppingListDao)

    @Bean
    fun getShoppingListsQuery(shoppingListDao: ShoppingListDao) = GetShoppingListsQuery(shoppingListDao)

}
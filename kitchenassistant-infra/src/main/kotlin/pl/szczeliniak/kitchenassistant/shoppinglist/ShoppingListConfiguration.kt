package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.*
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListFactory
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListItemFactory
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListsQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.ShoppingListConverter

@Configuration
class ShoppingListConfiguration {

    @Bean
    fun shoppingListItemFactory(getReceiptQuery: GetReceiptQuery): ShoppingListItemFactory =
        ShoppingListItemFactory(getReceiptQuery)

    @Bean
    fun shoppingListFactory(shoppingListItemFactory: ShoppingListItemFactory): ShoppingListFactory =
        ShoppingListFactory(shoppingListItemFactory)

    @Bean
    fun addShoppingListCommand(shoppingListDao: ShoppingListDao, shoppingListFactory: ShoppingListFactory) =
        AddShoppingListCommand(shoppingListDao, shoppingListFactory)

    @Bean
    fun addShoppingListItemCommand(
        shoppingListDao: ShoppingListDao,
        shoppingListItemDao: ShoppingListItemDao,
        shoppingListItemFactory: ShoppingListItemFactory
    ) =
        AddShoppingListItemCommand(shoppingListDao, shoppingListItemDao, shoppingListItemFactory)

    @Bean
    fun updateShoppingListCommand(shoppingListDao: ShoppingListDao) = UpdateShoppingListCommand(shoppingListDao)

    @Bean
    fun deleteShoppingListCommand(shoppingListDao: ShoppingListDao) = DeleteShoppingListCommand(shoppingListDao)

    @Bean
    fun deleteShoppingListItemCommand(shoppingListDao: ShoppingListDao, shoppingListItemDao: ShoppingListItemDao) =
        DeleteShoppingListItemCommand(shoppingListDao, shoppingListItemDao)

    @Bean
    fun updateShoppingListItemCommand(shoppingListDao: ShoppingListDao, shoppingListItemDao: ShoppingListItemDao) =
        UpdateShoppingListItemCommand(shoppingListDao, shoppingListItemDao)

    @Bean
    fun markShoppingListItemAsDoneCommand(shoppingListDao: ShoppingListDao, shoppingListItemDao: ShoppingListItemDao) =
        MarkItemAsCompletedCommand(shoppingListDao, shoppingListItemDao)

    @Bean
    fun markShoppingListAsArchivedCommand(shoppingListDao: ShoppingListDao) =
        MarkShoppingListAsArchivedCommand(shoppingListDao)

    @Bean
    fun shoppingListConverter(getReceiptQuery: GetReceiptQuery) = ShoppingListConverter(getReceiptQuery)

    @Bean
    fun getShoppingListQuery(shoppingListDao: ShoppingListDao, shoppingListConverter: ShoppingListConverter) =
        GetShoppingListQuery(shoppingListDao, shoppingListConverter)

    @Bean
    fun getShoppingListsQuery(shoppingListDao: ShoppingListDao, shoppingListConverter: ShoppingListConverter) =
        GetShoppingListsQuery(shoppingListDao, shoppingListConverter)

}
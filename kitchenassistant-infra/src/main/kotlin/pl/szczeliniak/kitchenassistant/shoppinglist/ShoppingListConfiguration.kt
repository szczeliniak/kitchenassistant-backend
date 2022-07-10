package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.receipt.ReceiptFacade
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.*
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListFactory
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListItemFactory
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListsQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.ShoppingListConverter

@Configuration
class ShoppingListConfiguration {

    @Bean
    fun deassignReceiptFromShoppingListsCommand(shoppingListDao: ShoppingListDao): DeassignReceiptFromShoppingListsCommand =
        DeassignReceiptFromShoppingListsCommand(shoppingListDao)

    @Bean
    fun shoppingListFacade(
        shoppingListDao: ShoppingListDao,
        receiptFacade: ReceiptFacade,
        shoppingListItemDao: ShoppingListItemDao,
        deassignReceiptFromShoppingListsCommand: DeassignReceiptFromShoppingListsCommand
    ): ShoppingListFacade {
        val shoppingListConverter = ShoppingListConverter(receiptFacade)
        val shoppingListItemFactory = ShoppingListItemFactory(receiptFacade)
        val shoppingListFactory = ShoppingListFactory(shoppingListItemFactory)
        return ShoppingListFacadeImpl(
            GetShoppingListQuery(shoppingListDao, shoppingListConverter),
            GetShoppingListsQuery(shoppingListDao, shoppingListConverter),
            AddShoppingListCommand(shoppingListDao, shoppingListFactory),
            UpdateShoppingListCommand(shoppingListDao),
            AddShoppingListItemCommand(shoppingListDao, shoppingListItemDao, shoppingListItemFactory),
            UpdateShoppingListItemCommand(shoppingListDao, shoppingListItemDao),
            MarkItemAsCompletedCommand(shoppingListDao, shoppingListItemDao),
            MarkShoppingListAsArchivedCommand(shoppingListDao),
            DeleteShoppingListCommand(shoppingListDao),
            DeleteShoppingListItemCommand(shoppingListDao, shoppingListItemDao),
            deassignReceiptFromShoppingListsCommand,
            ArchiveShoppingListsCommand(shoppingListDao)
        )
    }

}
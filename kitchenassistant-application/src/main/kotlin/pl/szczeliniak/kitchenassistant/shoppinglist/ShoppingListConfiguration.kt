package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.recipe.RecipeFacade
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.*
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListFactory
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListItemFactory
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItemDao
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListsQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.ShoppingListConverter

@Configuration
class ShoppingListConfiguration {

    @Bean
    fun deassignRecipeFromShoppingListsCommand(shoppingListDao: ShoppingListDao): DeleteRecipeFromShoppingListsCommand =
        DeleteRecipeFromShoppingListsCommand(shoppingListDao)

    @Bean
    fun shoppingListFacade(
        shoppingListDao: ShoppingListDao,
        recipeFacade: RecipeFacade,
        shoppingListItemDao: ShoppingListItemDao,
        deleteRecipeFromShoppingListsCommand: DeleteRecipeFromShoppingListsCommand
    ): ShoppingListFacade {
        val shoppingListConverter = ShoppingListConverter(recipeFacade)
        val shoppingListItemFactory = ShoppingListItemFactory(recipeFacade)
        val shoppingListFactory = ShoppingListFactory(shoppingListItemFactory)
        return ShoppingListFacade(
            GetShoppingListQuery(shoppingListDao, shoppingListConverter),
            GetShoppingListsQuery(shoppingListDao, shoppingListConverter),
            AddShoppingListCommand(shoppingListDao, shoppingListFactory),
            UpdateShoppingListCommand(shoppingListDao),
            AddShoppingListItemCommand(shoppingListDao, shoppingListItemDao, shoppingListItemFactory),
            UpdateShoppingListItemCommand(shoppingListDao, shoppingListItemDao),
            MarkItemAsCompletedCommand(shoppingListDao, shoppingListItemDao),
            MarkShoppingListAsArchivedCommand(shoppingListDao),
            DeleteShoppingListCommand(shoppingListDao),
            DeleteShoppingListItemCommand(shoppingListDao),
            ArchiveShoppingListsCommand(shoppingListDao)
        )
    }

}
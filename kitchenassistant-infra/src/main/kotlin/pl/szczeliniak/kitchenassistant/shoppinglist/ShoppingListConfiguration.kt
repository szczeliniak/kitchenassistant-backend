package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.recipe.RecipeFacade
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.*
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListFactory
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListItemFactory
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListsQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.ShoppingListConverter

@Configuration
class ShoppingListConfiguration {

    @Bean
    fun deassignRecipeFromShoppingListsCommand(shoppingListDao: ShoppingListDao): DeassignRecipeFromShoppingListsCommand =
        DeassignRecipeFromShoppingListsCommand(shoppingListDao)

    @Bean
    fun shoppingListFacade(
        shoppingListDao: ShoppingListDao,
        recipeFacade: RecipeFacade,
        shoppingListItemDao: ShoppingListItemDao,
        deassignRecipeFromShoppingListsCommand: DeassignRecipeFromShoppingListsCommand
    ): ShoppingListFacade {
        val shoppingListConverter = ShoppingListConverter(recipeFacade)
        val shoppingListItemFactory = ShoppingListItemFactory(recipeFacade)
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
            deassignRecipeFromShoppingListsCommand,
            ArchiveShoppingListsCommand(shoppingListDao)
        )
    }

}
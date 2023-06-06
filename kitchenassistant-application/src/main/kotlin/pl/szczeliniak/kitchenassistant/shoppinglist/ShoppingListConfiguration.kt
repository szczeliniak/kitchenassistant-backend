package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.*
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListFactory
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListItemFactory
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItemDao
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.GetShoppingListsQuery
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.ShoppingListConverter
import pl.szczeliniak.kitchenassistant.user.db.UserDao

@Configuration
class ShoppingListConfiguration {

    @Bean
    fun shoppingListFacade(
        shoppingListDao: ShoppingListDao,
        recipeDao: RecipeDao,
        shoppingListItemDao: ShoppingListItemDao,
        userDao: UserDao
    ): ShoppingListFacade {
        val shoppingListConverter = ShoppingListConverter()
        val shoppingListItemFactory = ShoppingListItemFactory(recipeDao)
        val shoppingListFactory = ShoppingListFactory(shoppingListItemFactory, userDao)
        return ShoppingListFacade(
            GetShoppingListQuery(shoppingListDao, shoppingListConverter),
            GetShoppingListsQuery(shoppingListDao, shoppingListConverter),
            AddShoppingListCommand(shoppingListDao, shoppingListFactory),
            UpdateShoppingListCommand(shoppingListDao),
            AddShoppingListItemCommand(shoppingListDao, shoppingListItemDao, shoppingListItemFactory),
            UpdateShoppingListItemCommand(shoppingListDao, shoppingListItemDao, recipeDao),
            MarkItemAsCompletedCommand(shoppingListDao, shoppingListItemDao),
            MarkShoppingListAsArchivedCommand(shoppingListDao),
            DeleteShoppingListCommand(shoppingListDao),
            DeleteShoppingListItemCommand(shoppingListDao),
            ArchiveShoppingListsCommand(shoppingListDao)
        )
    }

}
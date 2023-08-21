package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItemDao
import pl.szczeliniak.kitchenassistant.user.db.UserDao

@Configuration
class ShoppingListConfiguration {

    @Bean
    fun shoppingListMapper() = ShoppingListMapperImpl()

    @Bean
    fun shoppingListService(
        shoppingListMapper: ShoppingListMapper,
        shoppingListDao: ShoppingListDao,
        recipeDao: RecipeDao,
        shoppingListItemDao: ShoppingListItemDao,
        userDao: UserDao
    ) = ShoppingListService(shoppingListMapper, shoppingListDao, recipeDao, userDao)


    @Bean
    fun shoppingListItemService(
        shoppingListDao: ShoppingListDao,
        shoppingListItemDao: ShoppingListItemDao,
        recipeDao: RecipeDao
    ) = ShoppingListItemService(shoppingListDao, shoppingListItemDao, recipeDao)

}
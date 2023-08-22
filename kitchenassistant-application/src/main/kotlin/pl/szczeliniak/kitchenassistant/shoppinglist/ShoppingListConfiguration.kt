package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItemDao
import pl.szczeliniak.kitchenassistant.shoppinglist.mapper.ShoppingListMapperImpl
import pl.szczeliniak.kitchenassistant.user.db.UserDao

@Configuration
class ShoppingListConfiguration {

    @Bean
    fun shoppingListService(
        shoppingListDao: ShoppingListDao,
        recipeDao: RecipeDao,
        shoppingListItemDao: ShoppingListItemDao,
        userDao: UserDao,
        requestContext: RequestContext
    ) = ShoppingListService(ShoppingListMapperImpl(), shoppingListDao, recipeDao, userDao, requestContext)


    @Bean
    fun shoppingListItemService(
        shoppingListDao: ShoppingListDao,
        shoppingListItemDao: ShoppingListItemDao,
        recipeDao: RecipeDao
    ) = ShoppingListItemService(shoppingListDao, shoppingListItemDao, recipeDao)

}
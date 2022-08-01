package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao

open class DeassignRecipeFromShoppingListsCommand(
    private val shoppingListDao: ShoppingListDao
) {
    open fun execute(recipeId: Int): SuccessResponse {
        val shoppingLists = shoppingListDao.findAll(ShoppingListCriteria(recipeId = recipeId))
        shoppingLists.forEach {
            it.items.filter { item -> item.recipeId == recipeId }.forEach { item -> item.recipeId = null }
        }
        shoppingListDao.save(shoppingLists)
        return SuccessResponse(recipeId)
    }

}
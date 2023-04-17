package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao

open class DeleteRecipeFromShoppingListsCommand(
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
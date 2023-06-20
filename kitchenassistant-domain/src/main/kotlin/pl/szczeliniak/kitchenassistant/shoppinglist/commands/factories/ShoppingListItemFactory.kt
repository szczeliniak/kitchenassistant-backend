package pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem

open class ShoppingListItemFactory(private val recipeDao: RecipeDao) {

    open fun create(request: NewShoppingListItemRequest): ShoppingListItem {
        return ShoppingListItem(
            name = request.name,
            quantity = request.quantity,
            sequence = request.sequence,
            recipe = request.recipeId?.let {
                recipeDao.findById(it) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
            }
        )
    }

}
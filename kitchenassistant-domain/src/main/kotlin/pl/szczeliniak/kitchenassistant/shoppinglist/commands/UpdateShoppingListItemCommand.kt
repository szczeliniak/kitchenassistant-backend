package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListItemRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItemDao

class UpdateShoppingListItemCommand(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListItemDao: ShoppingListItemDao,
    private val recipeDao: RecipeDao
) {

    fun execute(shoppingListId: Int, ingredientId: Int, request: UpdateShoppingListItemRequest): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(shoppingListId)
                ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)

        val item =
            shoppingList.items.firstOrNull { it.id == ingredientId }
                ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_ITEM_NOT_FOUND)

        item.name = request.name
        item.quantity = request.quantity
        item.sequence = request.sequence
        item.recipe = request.recipeId?.let { recipeDao.findById(it) }

        return SuccessResponse(shoppingListItemDao.save(item).id)
    }

}
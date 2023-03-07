package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItemDao
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListItemDto

class UpdateShoppingListItemCommand(
        private val shoppingListDao: ShoppingListDao,
        private val shoppingListItemDao: ShoppingListItemDao
) {

    fun execute(shoppingListId: Int, ingredientId: Int, dto: UpdateShoppingListItemDto): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(shoppingListId)
                ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)

        val item =
            shoppingList.items.firstOrNull { it.id == ingredientId }
                ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_ITEM_NOT_FOUND)

        item.name = dto.name
        item.quantity = dto.quantity
        item.sequence = dto.sequence
        item.recipeId = dto.recipeId

        return SuccessResponse(shoppingListItemDao.save(item).id)
    }

}
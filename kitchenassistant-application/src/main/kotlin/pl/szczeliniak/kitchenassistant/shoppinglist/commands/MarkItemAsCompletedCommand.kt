package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItemDao

class MarkItemAsCompletedCommand(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListItemDao: ShoppingListItemDao
) {

    fun execute(shoppingListId: Int, itemId: Int, completed: Boolean): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(shoppingListId)
                ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)

        val shoppingListItem = shoppingList.items.firstOrNull { it.id == itemId }
            ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_ITEM_NOT_FOUND)
        shoppingListItem.completed = completed

        shoppingListItemDao.save(shoppingListItem)

        return SuccessResponse(shoppingListItem.id)
    }

}
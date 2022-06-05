package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItemDao

class DeleteShoppingListItemCommand(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListItemDao: ShoppingListItemDao
) {

    fun execute(shoppingListId: Int, shoppingListItemId: Int): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(shoppingListId)
                ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)
        val item = shoppingList.deleteItemById(shoppingListItemId)
        shoppingListItemDao.save(item)
        return SuccessResponse(shoppingList.id)
    }

}
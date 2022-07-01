package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao

class DeleteShoppingListCommand(
    private val shoppingListDao: ShoppingListDao
) {

    fun execute(id: Int): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)
        if (shoppingList.deleted) {
            throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_ALREADY_REMOVED)
        }
        shoppingList.deleted = true

        return SuccessResponse(shoppingListDao.save(shoppingList).id)
    }

}
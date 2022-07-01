package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao

class MarkShoppingListAsArchivedCommand(
    private val shoppingListDao: ShoppingListDao
) {

    fun execute(shoppingListId: Int, archived: Boolean): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(shoppingListId)
                ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)
        shoppingList.archived = archived
        shoppingListDao.save(shoppingList)
        return SuccessResponse(shoppingList.id)
    }

}
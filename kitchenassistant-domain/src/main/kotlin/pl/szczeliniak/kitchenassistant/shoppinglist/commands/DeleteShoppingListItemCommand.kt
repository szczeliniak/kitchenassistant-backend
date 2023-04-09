package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao

class DeleteShoppingListItemCommand(private val shoppingListDao: ShoppingListDao) {

    fun execute(shoppingListId: Int, shoppingListItemId: Int): SuccessResponse {
        shoppingListDao.findById(shoppingListId)?.let { shoppingList ->
            shoppingList.items.removeIf { it.id == shoppingListItemId }
            shoppingListDao.save(shoppingList)
        }
        return SuccessResponse(shoppingListItemId)
    }

}
package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao

class DeleteShoppingListCommand(
    private val shoppingListDao: ShoppingListDao
) {

    fun execute(id: Int): SuccessResponse {
        shoppingListDao.findById(id)?.let {
            shoppingListDao.delete(it)
        }
        return SuccessResponse(id)
    }

}
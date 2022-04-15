package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao

class DeleteShoppingListCommand(
    private val shoppingListDao: ShoppingListDao
) {

    fun execute(id: Int): SuccessResponse {
        val shoppingList = shoppingListDao.findById(id) ?: throw NotFoundException("Shopping list not found")
        shoppingList.markAsDeleted()

        return SuccessResponse(shoppingListDao.save(shoppingList).id)
    }

}
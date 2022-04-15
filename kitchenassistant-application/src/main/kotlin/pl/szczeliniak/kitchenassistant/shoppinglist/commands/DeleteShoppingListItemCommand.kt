package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao

class DeleteShoppingListItemCommand(
    private val shoppingListDao: ShoppingListDao
) {

    fun execute(shoppingListId: Int, shoppingListItemId: Int): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(shoppingListId) ?: throw NotFoundException("Shopping list not found")
        val item = shoppingList.deleteItemById(shoppingListItemId)
        shoppingListDao.save(shoppingList)

        return SuccessResponse(item.id)
    }

}
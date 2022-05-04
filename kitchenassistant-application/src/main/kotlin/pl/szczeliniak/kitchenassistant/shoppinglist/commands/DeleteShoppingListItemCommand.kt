package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItemDao

class DeleteShoppingListItemCommand(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListItemDao: ShoppingListItemDao
) {

    fun execute(shoppingListId: Int, shoppingListItemId: Int): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(shoppingListId) ?: throw NotFoundException("Shopping list not found")
        val item = shoppingList.deleteItemById(shoppingListItemId)
        shoppingListItemDao.save(item)
        return SuccessResponse(shoppingList.id)
    }

}
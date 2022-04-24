package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItemDao

class MarkItemAsCompletedCommand(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListItemDao: ShoppingListItemDao
) {

    fun execute(shoppingListId: Int, itemId: Int, completed: Boolean): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(shoppingListId) ?: throw NotFoundException("Shopping list not found")

        val shoppingListItem = shoppingList.items.firstOrNull { it.id == itemId }
            ?: throw NotFoundException("Shopping list item not found")
        shoppingListItem.markAsCompleted(completed)

        shoppingListItemDao.save(shoppingListItem)

        return SuccessResponse(shoppingListItem.id)
    }

}
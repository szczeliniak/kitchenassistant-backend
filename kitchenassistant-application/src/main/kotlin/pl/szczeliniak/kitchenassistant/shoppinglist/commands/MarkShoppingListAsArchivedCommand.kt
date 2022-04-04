package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao

class MarkShoppingListAsArchivedCommand(
    private val shoppingListDao: ShoppingListDao
) {

    fun execute(shoppingListId: Int, archived: Boolean): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(shoppingListId) ?: throw NotFoundException("Shopping list not found")

        shoppingList.markAsArchived(archived)

        shoppingListDao.save(shoppingList)

        return SuccessResponse(shoppingList.id)
    }

}
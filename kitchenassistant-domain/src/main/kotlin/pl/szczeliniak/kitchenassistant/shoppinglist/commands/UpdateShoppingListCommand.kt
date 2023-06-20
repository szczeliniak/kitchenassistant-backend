package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListRequest

class UpdateShoppingListCommand(private val shoppingListDao: ShoppingListDao) {

    fun execute(id: Int, request: UpdateShoppingListRequest): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)
        shoppingList.name = request.name
        shoppingList.description = request.description
        shoppingList.date = request.date
        shoppingList.automaticArchiving = request.automaticArchiving
        return SuccessResponse(shoppingListDao.save(shoppingList).id)
    }

}
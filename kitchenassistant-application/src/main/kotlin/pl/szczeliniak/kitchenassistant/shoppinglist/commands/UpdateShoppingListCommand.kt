package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListDto

class UpdateShoppingListCommand(private val shoppingListDao: ShoppingListDao) {

    fun execute(id: Int, dto: UpdateShoppingListDto): SuccessResponse {
        val shoppingList =
            shoppingListDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)
        shoppingList.name = dto.name
        shoppingList.description = dto.description
        shoppingList.date = dto.date
        return SuccessResponse(shoppingListDao.save(shoppingList).id)
    }

}
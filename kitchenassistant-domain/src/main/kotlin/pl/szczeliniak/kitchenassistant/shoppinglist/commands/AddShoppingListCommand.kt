package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListFactory

class AddShoppingListCommand(
        private val shoppingListDao: ShoppingListDao,
        private val shoppingListFactory: ShoppingListFactory
) {

    fun execute(request: NewShoppingListRequest): SuccessResponse {
        return SuccessResponse(shoppingListDao.save(shoppingListFactory.create(request)).id)
    }

}
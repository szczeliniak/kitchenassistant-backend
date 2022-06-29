package pl.szczeliniak.kitchenassistant.shoppinglist.queries

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListResponse

class GetShoppingListQuery(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListConverter: ShoppingListConverter
) {

    fun execute(userId: Int): ShoppingListResponse {
        return ShoppingListResponse(
            shoppingListConverter.mapDetails(
                shoppingListDao.findById(userId) ?: throw KitchenAssistantException(ErrorCode.SHOPPING_LIST_NOT_FOUND)
            )
        )
    }

}
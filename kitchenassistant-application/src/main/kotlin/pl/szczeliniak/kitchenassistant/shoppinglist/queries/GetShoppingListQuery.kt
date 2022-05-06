package pl.szczeliniak.kitchenassistant.shoppinglist.queries

import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListResponse

class GetShoppingListQuery(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListConverter: ShoppingListConverter
) {

    fun execute(userId: Int): ShoppingListResponse {
        return ShoppingListResponse(
            shoppingListConverter.map(
                shoppingListDao.findById(userId) ?: throw NotFoundException("Shopping list not found")
            )
        )
    }

}
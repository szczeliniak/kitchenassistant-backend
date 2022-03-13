package pl.szczeliniak.kitchenassistant.shoppinglist.queries

import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListResponse

class GetShoppingListQuery(private val shoppingListDao: ShoppingListDao) {

    fun execute(userId: Int): ShoppingListResponse {
        return ShoppingListResponse(
            ShoppingListDto.fromDomain(
                shoppingListDao.findById(userId) ?: throw NotFoundException("Shopping list not found")
            )
        )
    }

}
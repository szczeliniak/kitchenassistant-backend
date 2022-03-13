package pl.szczeliniak.kitchenassistant.shoppinglist.queries

import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListsResponse

class GetShoppingListsQuery(private val shoppingListDao: ShoppingListDao) {

    fun execute(criteria: ShoppingListCriteria): ShoppingListsResponse {
        return ShoppingListsResponse(shoppingListDao.findAll(criteria)
            .map { ShoppingListDto.fromDomain(it) })
    }

}
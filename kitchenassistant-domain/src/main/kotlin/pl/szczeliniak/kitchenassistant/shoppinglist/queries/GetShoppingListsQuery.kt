package pl.szczeliniak.kitchenassistant.shoppinglist.queries

import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination
import pl.szczeliniak.kitchenassistant.shared.PaginationUtils
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListsResponse

class GetShoppingListsQuery(
        private val shoppingListDao: ShoppingListDao,
        private val shoppingListConverter: ShoppingListConverter
) {

    fun execute(page: Long?, limit: Int?, criteria: ShoppingListCriteria): ShoppingListsResponse {
        val currentPage = PaginationUtils.calculatePageNumber(page)
        val currentLimit = PaginationUtils.calculateLimit(limit)
        val offset = PaginationUtils.calculateOffset(currentPage, currentLimit)
        val totalNumberOfPages = PaginationUtils.calculateNumberOfPages(currentLimit, shoppingListDao.count(criteria))
        return ShoppingListsResponse(
            shoppingListDao.findAll(criteria, offset, currentLimit)
                .map { shoppingListConverter.map(it) }.toSet(),
            Pagination(currentPage, currentLimit, totalNumberOfPages)
        )
    }

}
package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.CategoryCriteria
import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.CategoriesResponse

class GetCategoriesQuery(private val categoryDao: CategoryDao, private val receiptConverter: ReceiptConverter) {

    fun execute(criteria: CategoryCriteria): CategoriesResponse {
        return CategoriesResponse(categoryDao.findAll(criteria).map { receiptConverter.map(it) }.toSet())
    }

}
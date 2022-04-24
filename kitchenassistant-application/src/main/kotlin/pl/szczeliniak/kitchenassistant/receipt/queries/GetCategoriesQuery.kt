package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.CategoryCriteria
import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.CategoriesResponse
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.CategoryDto

class GetCategoriesQuery(private val categoryDao: CategoryDao) {

    fun execute(criteria: CategoryCriteria): CategoriesResponse {
        return CategoriesResponse(categoryDao.findAll(criteria).map { CategoryDto.fromDomain(it) }.toSet())
    }

}
package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.RecipesResponse
import pl.szczeliniak.kitchenassistant.shared.PaginationUtils
import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination

class GetRecipesQuery(private val recipeDao: RecipeDao, private val recipeConverter: RecipeConverter) {

    fun execute(page: Long? = null, limit: Int? = null, criteria: RecipeCriteria): RecipesResponse {
        val currentPage = PaginationUtils.calculatePageNumber(page)
        val currentLimit = PaginationUtils.calculateLimit(limit)
        val offset = PaginationUtils.calculateOffset(currentPage, currentLimit)
        val totalNumberOfPages = PaginationUtils.calculateNumberOfPages(currentLimit, recipeDao.count(criteria))
        return RecipesResponse(
            recipeDao.findAll(criteria, offset, currentLimit).map { recipeConverter.map(it) },
            Pagination(currentPage, currentLimit, totalNumberOfPages)
        )
    }

}
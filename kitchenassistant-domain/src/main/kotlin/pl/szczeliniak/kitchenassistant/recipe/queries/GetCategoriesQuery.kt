package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.db.CategoryCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.CategoriesResponse

class GetCategoriesQuery(private val categoryDao: CategoryDao, private val recipeConverter: RecipeConverter) {

    fun execute(criteria: CategoryCriteria): CategoriesResponse {
        return CategoriesResponse(categoryDao.findAll(criteria).map { recipeConverter.map(it) }.toSet())
    }

}
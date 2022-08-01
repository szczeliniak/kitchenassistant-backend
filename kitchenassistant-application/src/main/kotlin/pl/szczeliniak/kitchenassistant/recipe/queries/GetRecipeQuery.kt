package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.RecipeResponse
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException

open class GetRecipeQuery(private val recipeDao: RecipeDao, private val recipeConverter: RecipeConverter) {

    open fun execute(id: Int): RecipeResponse {
        return RecipeResponse(
            recipeConverter.mapDetails(
                recipeDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
            )
        )
    }

}
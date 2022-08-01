package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class MarkRecipeAsFavoriteCommand(
    private val recipeDao: RecipeDao,
) {

    fun execute(id: Int, isFavorite: Boolean): SuccessResponse {
        val recipe = recipeDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        recipe.favorite = isFavorite
        return SuccessResponse(recipeDao.save(recipe).id)
    }

}
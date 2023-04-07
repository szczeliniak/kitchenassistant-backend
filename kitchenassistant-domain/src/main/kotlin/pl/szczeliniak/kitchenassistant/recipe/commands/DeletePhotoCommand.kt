package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeletePhotoCommand(private val recipeDao: RecipeDao) {

    fun execute(recipeId: Int): SuccessResponse {
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        if (recipe.photo == null) {
            return SuccessResponse(recipe.id)
        }
        recipe.photo = null
        return SuccessResponse(recipeDao.save(recipe).id)
    }

}
package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteStepCommand(private val recipeDao: RecipeDao) {

    fun execute(recipeId: Int, stepId: Int): SuccessResponse {
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        val step =
            recipe.steps.firstOrNull { it.id == stepId } ?: throw KitchenAssistantException(ErrorCode.STEP_NOT_FOUND)
        if (step.deleted) {
            throw KitchenAssistantException(ErrorCode.STEP_ALREADY_REMOVED)
        }
        step.deleted = true

        recipeDao.save(recipe)
        return SuccessResponse(step.id)
    }

}
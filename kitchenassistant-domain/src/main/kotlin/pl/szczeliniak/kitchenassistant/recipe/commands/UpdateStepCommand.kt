package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.commands.dto.UpdateStepRequest
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class UpdateStepCommand(private val recipeDao: RecipeDao) {

    fun execute(recipeId: Int, stepId: Int, request: UpdateStepRequest): SuccessResponse {
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)

        val step =
            recipe.steps.firstOrNull { it.id == stepId } ?: throw KitchenAssistantException(ErrorCode.STEP_NOT_FOUND)

        step.photoName = request.photoName
        step.description = request.description
        step.sequence = request.sequence

        recipeDao.save(recipe)

        return SuccessResponse(step.id)
    }

}
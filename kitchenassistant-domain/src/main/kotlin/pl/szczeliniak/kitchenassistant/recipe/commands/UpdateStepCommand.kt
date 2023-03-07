package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.UpdateStepDto
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class UpdateStepCommand(private val recipeDao: RecipeDao) {

    fun execute(recipeId: Int, stepId: Int, dto: UpdateStepDto): SuccessResponse {
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)

        val step =
            recipe.steps.firstOrNull { it.id == stepId } ?: throw KitchenAssistantException(ErrorCode.STEP_NOT_FOUND)

        step.name = dto.name
        step.description = dto.description
        step.sequence = dto.sequence

        recipeDao.save(recipe)

        return SuccessResponse(step.id)
    }

}
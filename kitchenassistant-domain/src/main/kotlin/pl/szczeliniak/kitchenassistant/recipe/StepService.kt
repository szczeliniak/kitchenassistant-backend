package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.db.Step
import pl.szczeliniak.kitchenassistant.recipe.db.StepDao
import pl.szczeliniak.kitchenassistant.recipe.dto.request.NewStepRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.request.UpdateStepRequest
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.FtpClient
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

open class StepService(
    private val recipeDao: RecipeDao,
    private val stepDao: StepDao,
    private val ftpClient: FtpClient
) {

    fun add(recipeId: Int, request: NewStepRequest): SuccessResponse {
        request.photoName?.let { if (!ftpClient.exists(it)) throw KitchenAssistantException(ErrorCode.PHOTO_NOT_FOUND) }
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        val step = stepDao.save(createStep(request))
        recipe.steps.add(step)
        recipeDao.save(recipe)
        return SuccessResponse(step.id)
    }

    private fun createStep(request: NewStepRequest): Step {
        return Step(0, request.description, request.photoName, request.sequence)
    }

    fun update(recipeId: Int, stepId: Int, request: UpdateStepRequest): SuccessResponse {
        request.photoName?.let { if (!ftpClient.exists(it)) throw KitchenAssistantException(ErrorCode.PHOTO_NOT_FOUND) }

        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)

        val step =
            recipe.steps.firstOrNull { it.id == stepId } ?: throw KitchenAssistantException(ErrorCode.STEP_NOT_FOUND)

        step.photoName = request.photoName
        step.description = request.description
        step.sequence = request.sequence

        recipeDao.save(recipe)

        return SuccessResponse(step.id)
    }

    fun delete(recipeId: Int, stepId: Int): SuccessResponse {
        recipeDao.findById(recipeId)?.let { recipe ->
            recipe.steps.firstOrNull { it.id == stepId }?.let {
                stepDao.delete(it)
            } ?: throw KitchenAssistantException(ErrorCode.STEP_NOT_FOUND)
        }
        return SuccessResponse(stepId)
    }

}
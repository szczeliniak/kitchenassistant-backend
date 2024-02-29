package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.db.Step
import pl.szczeliniak.kitchenassistant.recipe.db.StepDao
import pl.szczeliniak.kitchenassistant.recipe.dto.request.UpdateStepsRequest
import pl.szczeliniak.kitchenassistant.shared.BaseService
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

open class StepService(
    private val recipeDao: RecipeDao,
    private val stepDao: StepDao,
    requestContext: RequestContext
) : BaseService(requestContext) {

    fun updateSteps(recipeId: Int, request: UpdateStepsRequest): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val recipe = recipeDao.findById(recipeId, requestContext.userId()) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        recipe.steps.forEach { stepDao.delete(it) }
        recipe.steps.clear()
        request.steps.forEach {
            recipe.steps.add(createStep(it))
        }
        recipeDao.save(recipe)
        return SuccessResponse(recipe.id)
    }

    private fun createStep(step: UpdateStepsRequest.Step): Step {
        return Step(0, step.description, step.sequence)
    }

    fun delete(recipeId: Int, stepId: Int): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val recipe = recipeDao.findById(recipeId, requestContext.userId()) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        val step = recipe.steps.firstOrNull { it.id == stepId } ?: throw KitchenAssistantException(ErrorCode.STEP_NOT_FOUND)
        stepDao.delete(step)
        return SuccessResponse(stepId)
    }

}
package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.db.StepDao
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

    fun delete(recipeId: Int, stepGroupId: Int, stepId: Int): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val recipe = recipeDao.findById(recipeId, requestContext.userId()) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        val stepGroup =
            recipe.stepGroups.firstOrNull { ingredientGroup -> ingredientGroup.id == stepGroupId } ?: throw KitchenAssistantException(ErrorCode.STEP_GROUP_NOT_FOUND)
        val step = stepGroup.steps.firstOrNull { ingredient -> ingredient.id == stepId } ?: throw KitchenAssistantException(ErrorCode.STEP_NOT_FOUND)
        stepDao.delete(step)
        return SuccessResponse(stepGroupId)
    }

}
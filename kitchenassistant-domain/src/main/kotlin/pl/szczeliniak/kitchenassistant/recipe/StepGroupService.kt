package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.db.StepDao
import pl.szczeliniak.kitchenassistant.recipe.db.StepGroupDao
import pl.szczeliniak.kitchenassistant.shared.BaseService
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

open class StepGroupService(
    private val recipeDao: RecipeDao,
    private val stepDao: StepDao,
    private val stepGroupDao: StepGroupDao,
    requestContext: RequestContext
) : BaseService(requestContext) {

    fun delete(recipeId: Int, stepGroupId: Int): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val recipe = recipeDao.findById(recipeId, requestContext.userId()) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        val stepGroup =
            recipe.stepGroups.firstOrNull { stepGroup -> stepGroup.id == stepGroupId }
                ?: throw KitchenAssistantException(ErrorCode.STEP_GROUP_NOT_FOUND)
        stepGroup.steps.forEach { ingredient ->
            stepDao.delete(ingredient)
        }
        stepGroupDao.delete(stepGroup)
        return SuccessResponse(stepGroupId)
    }

}
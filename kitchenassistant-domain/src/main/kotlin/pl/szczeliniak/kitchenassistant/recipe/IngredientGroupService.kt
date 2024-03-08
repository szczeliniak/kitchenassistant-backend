package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.IngredientDao
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroupDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.BaseService
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

open class IngredientGroupService(
    private val recipeDao: RecipeDao,
    private val ingredientGroupDao: IngredientGroupDao,
    private val ingredientDao: IngredientDao,
    requestContext: RequestContext
) : BaseService(requestContext) {

    fun delete(recipeId: Int, ingredientGroupId: Int): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val recipe = recipeDao.findById(recipeId, requestContext.userId()) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        val ingredientGroup =
            recipe.ingredientGroups.firstOrNull { ingredientGroup -> ingredientGroup.id == ingredientGroupId }
                ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_GROUP_NOT_FOUND)
        ingredientGroup.ingredients.forEach { ingredient ->
            ingredientDao.delete(ingredient)
        }
        ingredientGroupDao.delete(ingredientGroup)
        return SuccessResponse(ingredientGroupId)
    }

}
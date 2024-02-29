package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.IngredientDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.BaseService
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

open class IngredientService(
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao,
    requestContext: RequestContext
) : BaseService(requestContext) {

    fun delete(recipeId: Int, ingredientGroupId: Int, ingredientId: Int): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val recipe = recipeDao.findById(recipeId, requestContext.userId()) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        val ingredientGroup =
            recipe.ingredientGroups.firstOrNull { ingredientGroup -> ingredientGroup.id == ingredientGroupId } ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_GROUP_NOT_FOUND)
        val ingredient = ingredientGroup.ingredients.firstOrNull { ingredient -> ingredient.id == ingredientId } ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_NOT_FOUND)
        ingredientDao.delete(ingredient)
        return SuccessResponse(ingredientGroupId)
    }

}
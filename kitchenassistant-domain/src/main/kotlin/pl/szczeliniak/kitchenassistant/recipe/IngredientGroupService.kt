package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.Ingredient
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientDao
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroup
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroupDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.dto.request.UpdateIngredientGroupsRequest
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

    fun update(recipeId: Int, request: UpdateIngredientGroupsRequest): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val recipe = recipeDao.findById(recipeId, requestContext.userId()) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        recipe.ingredientGroups.forEach { ingredientGroup ->
            ingredientGroup.ingredients.forEach { ingredient -> ingredientDao.delete(ingredient) }
            ingredientGroupDao.delete(ingredientGroup)
        }
        recipe.ingredientGroups.clear()
        request.ingredientGroups.forEach { recipe.ingredientGroups.add(createIngredientGroup(it)) }
        recipeDao.save(recipe)
        return SuccessResponse(recipe.id)
    }

    private fun createIngredientGroup(ingredientGroup: UpdateIngredientGroupsRequest.IngredientGroup): IngredientGroup {
        return IngredientGroup(
            0,
            ingredientGroup.name,
            ingredientGroup.ingredients.map { createIngredient(it) }.toMutableList()
        )
    }

    private fun createIngredient(ingredient: UpdateIngredientGroupsRequest.IngredientGroup.Ingredient): Ingredient {
        return Ingredient(0, ingredient.name, ingredient.quantity)
    }

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
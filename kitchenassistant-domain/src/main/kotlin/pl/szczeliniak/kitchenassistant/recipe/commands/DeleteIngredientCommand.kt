package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteIngredientCommand(private val recipeDao: RecipeDao) {

    fun execute(recipeId: Int, ingredientGroupId: Int, ingredientId: Int): SuccessResponse {
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        val ingredientGroup = recipe.ingredientGroups.firstOrNull { ingredientGroupId == it.id }
            ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_GROUP_NOT_FOUND)

        val ingredient =
            ingredientGroup.ingredients.firstOrNull { it.id == ingredientId } ?: throw KitchenAssistantException(
                ErrorCode.INGREDIENT_NOT_FOUND
            )

        if (ingredient.deleted) {
            throw KitchenAssistantException(ErrorCode.INGREDIENT_ALREADY_REMOVED)
        }

        ingredient.deleted = true

        recipeDao.save(recipe)
        return SuccessResponse(ingredientId)
    }

}
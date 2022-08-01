package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteIngredientGroupCommand(private val recipeDao: RecipeDao) {

    fun execute(recipeId: Int, ingredientGroupId: Int): SuccessResponse {
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        val ingredientGroup = recipe.ingredientGroups.firstOrNull { it.id == ingredientGroupId }
            ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_GROUP_NOT_FOUND)
        ingredientGroup.deleted = true
        recipeDao.save(recipe)
        return SuccessResponse(ingredientGroup.id)
    }

}
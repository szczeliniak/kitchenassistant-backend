package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteIngredientCommand(private val recipeDao: RecipeDao) {

    fun execute(recipeId: Int, ingredientGroupId: Int, ingredientId: Int): SuccessResponse {
        recipeDao.findById(recipeId)?.let { recipe ->
            recipe.ingredientGroups.firstOrNull { ingredientGroupId == it.id }?.let { ingredientGroup ->
                ingredientGroup.ingredients.removeIf { it.id == ingredientId }
                recipeDao.save(recipe)
            }
        }
        return SuccessResponse(ingredientId)
    }

}
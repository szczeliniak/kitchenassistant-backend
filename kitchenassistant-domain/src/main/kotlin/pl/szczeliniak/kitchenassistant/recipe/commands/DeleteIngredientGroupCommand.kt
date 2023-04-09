package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteIngredientGroupCommand(private val recipeDao: RecipeDao) {

    fun execute(recipeId: Int, ingredientGroupId: Int): SuccessResponse {
        recipeDao.findById(recipeId)?.let { recipe ->
            recipe.ingredientGroups.removeIf { it.id == ingredientGroupId }
            recipeDao.save(recipe)
        }
        return SuccessResponse(ingredientGroupId)
    }

}
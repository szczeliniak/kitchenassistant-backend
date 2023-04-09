package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteStepCommand(private val recipeDao: RecipeDao) {

    fun execute(recipeId: Int, stepId: Int): SuccessResponse {
        recipeDao.findById(recipeId)?.let { recipe ->
            recipe.steps.removeIf { it.id == stepId }
            recipeDao.save(recipe)
        }
        return SuccessResponse(stepId)
    }

}
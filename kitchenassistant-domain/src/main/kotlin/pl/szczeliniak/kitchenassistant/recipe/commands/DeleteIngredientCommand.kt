package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.IngredientDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteIngredientCommand(private val recipeDao: RecipeDao, private val ingredientDao: IngredientDao) {

    fun execute(recipeId: Int, ingredientGroupId: Int, ingredientId: Int): SuccessResponse {
        recipeDao.findById(recipeId)?.let { recipe ->
            recipe.ingredientGroups.firstOrNull { ingredientGroup -> ingredientGroup.id == ingredientGroupId }
                ?.let { ingredientGroup ->
                    ingredientGroup.ingredients.firstOrNull { ingredient -> ingredient.id == ingredientId }
                        ?.let { ingredient -> ingredientDao.delete(ingredient) }
                }
        }
        return SuccessResponse(ingredientId)
    }

}
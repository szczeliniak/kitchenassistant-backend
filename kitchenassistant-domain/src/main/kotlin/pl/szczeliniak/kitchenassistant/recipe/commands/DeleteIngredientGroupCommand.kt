package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.IngredientDao
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroupDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteIngredientGroupCommand(
    private val recipeDao: RecipeDao,
    private val ingredientGroupDao: IngredientGroupDao,
    private val ingredientDao: IngredientDao
) {

    fun execute(recipeId: Int, ingredientGroupId: Int): SuccessResponse {
        recipeDao.findById(recipeId)?.let { recipe ->
            recipe.ingredientGroups.firstOrNull { ingredientGroup -> ingredientGroup.id == ingredientGroupId }
                ?.let { ingredientGroup ->
                    ingredientGroup.ingredients.forEach { ingredient ->
                        ingredientDao.delete(ingredient)
                    }
                    ingredientGroupDao.delete(ingredientGroup)
                }
        }
        return SuccessResponse(ingredientGroupId)
    }

}
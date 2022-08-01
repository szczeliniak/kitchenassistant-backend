package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.UpdateIngredientDto
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class UpdateIngredientCommand(private val recipeDao: RecipeDao) {

    fun execute(recipeId: Int, ingredientGroupId: Int, ingredientId: Int, dto: UpdateIngredientDto): SuccessResponse {
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        val ingredientGroup = recipe.ingredientGroups.firstOrNull { ingredientGroupId == it.id }
            ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_GROUP_NOT_FOUND)
        val ingredient = ingredientGroup.ingredients.firstOrNull { ingredientId == it.id }
            ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_NOT_FOUND)

        ingredient.name = dto.name
        ingredient.quantity = dto.quantity

        if (ingredientGroup.id != dto.ingredientGroupId) {
            val newIngredientGroup = recipe.ingredientGroups.firstOrNull { it.id == dto.ingredientGroupId }
                ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_GROUP_NOT_FOUND)
            ingredientGroup.ingredients.removeIf { ingredient.id == it.id }
            newIngredientGroup.ingredients.add(ingredient)
        }

        recipeDao.save(recipe)

        return SuccessResponse(ingredient.id)
    }

}
package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.IngredientDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewIngredientDto
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.IngredientFactory
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AddIngredientCommand(
        private val recipeDao: RecipeDao,
        private val ingredientDao: IngredientDao,
        private val ingredientFactory: IngredientFactory
) {

    fun execute(recipeId: Int, ingredientGroupId: Int?, dto: NewIngredientDto): SuccessResponse {
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)

        val ingredientGroup =
            ingredientGroupId?.let { recipe.ingredientGroups.firstOrNull { ingredientGroupId == it.id } }
                ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_GROUP_NOT_FOUND)

        val ingredient = ingredientDao.save(ingredientFactory.create(dto))
        ingredientGroup.ingredients.add(ingredient)

        recipeDao.save(recipe)
        return SuccessResponse(ingredient.id)
    }

}
package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroupDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewIngredientGroupDto
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.IngredientGroupFactory
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AddIngredientGroupCommand(
        private val recipeDao: RecipeDao,
        private val ingredientGroupFactory: IngredientGroupFactory,
        private val ingredientGroupDao: IngredientGroupDao
) {

    fun execute(recipeId: Int, dto: NewIngredientGroupDto): SuccessResponse {
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        val ingredientGroup = ingredientGroupDao.save(ingredientGroupFactory.create(dto))
        recipe.ingredientGroups.add(ingredientGroup)
        recipeDao.save(recipe)
        return SuccessResponse(ingredientGroup.id)
    }

}
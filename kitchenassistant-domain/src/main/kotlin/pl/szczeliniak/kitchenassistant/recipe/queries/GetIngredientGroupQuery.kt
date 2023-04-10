package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.IngredientGroupResponse
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException

class GetIngredientGroupQuery(
    private val recipeDao: RecipeDao,
    private val ingredientGroupConverter: IngredientGroupConverter
) {

    fun execute(recipeId: Int, ingredientGroupId: Int): IngredientGroupResponse {
        return IngredientGroupResponse(
            ingredientGroupConverter.map(recipeDao.findById(recipeId)?.let { recipe ->
                recipe.ingredientGroups.firstOrNull { ingredientGroup -> ingredientGroup.id == ingredientGroupId }
            } ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_GROUP_NOT_FOUND))
        )
    }

}
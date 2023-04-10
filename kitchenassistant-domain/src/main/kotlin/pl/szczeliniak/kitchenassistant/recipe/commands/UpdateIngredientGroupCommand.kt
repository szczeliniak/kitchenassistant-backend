package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.commands.dto.UpdateIngredientGroupDto
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.IngredientFactory
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientDao
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroupDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class UpdateIngredientGroupCommand(
    private val recipeDao: RecipeDao,
    private val ingredientGroupDao: IngredientGroupDao,
    private val ingredientDao: IngredientDao,
    private val ingredientFactory: IngredientFactory
) {

    fun execute(recipeId: Int, ingredientGroupId: Int, request: UpdateIngredientGroupDto): SuccessResponse {

        recipeDao.findById(recipeId)?.let { recipe ->
            recipe.ingredientGroups.firstOrNull { ingredientGroup -> ingredientGroup.id == ingredientGroupId }
                ?.let { ingredientGroup ->
                    ingredientGroup.name = request.name

                    val ids = request.ingredients.map { it.id }
                    ingredientGroup.ingredients.filter { !ids.contains(it.id) }.forEach {
                        ingredientGroup.ingredients.remove(it)
                        ingredientDao.delete(it)
                    }

                    request.ingredients
                        .filter { ingredient -> ingredient.id != null }
                        .forEach { ingredient ->
                            ingredientGroup.ingredients.firstOrNull { it.id == ingredient.id }?.let {
                                it.name = ingredient.name
                                it.quantity = ingredient.quantity
                            }
                        }

                    ingredientGroup.ingredients.addAll(request.ingredients
                        .filter { ingredient -> ingredient.id == null }
                        .map { ingredient -> ingredientFactory.create(ingredient.name, ingredient.quantity) })

                    ingredientGroupDao.save(ingredientGroup)
                }
        }
        return SuccessResponse(ingredientGroupId)
    }

}
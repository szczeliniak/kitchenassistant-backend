package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.recipe.dto.request.NewIngredientGroupRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.request.UpdateIngredientGroupRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.response.IngredientGroupResponse
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

open class IngredientGroupService(
    private val recipeDao: RecipeDao,
    private val ingredientGroupDao: IngredientGroupDao,
    private val ingredientDao: IngredientDao,
    private val ingredientGroupMapper: IngredientGroupMapper
) {


    fun add(recipeId: Int, request: NewIngredientGroupRequest): SuccessResponse {
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        val ingredientGroup = ingredientGroupDao.save(createIngredientGroup(request))
        recipe.ingredientGroups.add(ingredientGroup)
        recipeDao.save(recipe)
        return SuccessResponse(ingredientGroup.id)
    }

    private fun createIngredientGroup(request: NewIngredientGroupRequest): IngredientGroup {
        return IngredientGroup(
            0,
            request.name,
            request.ingredients.map { createIngredient(it) }.toMutableSet()
        )
    }

    private fun createIngredient(request: NewIngredientGroupRequest.NewIngredientRequest): Ingredient {
        return Ingredient(0, request.name, request.quantity)
    }

    fun update(
        recipeId: Int,
        ingredientGroupId: Int,
        request: UpdateIngredientGroupRequest
    ): SuccessResponse {
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
                        .map { ingredient -> createIngredient(ingredient) })

                    ingredientGroupDao.save(ingredientGroup)
                }
        }
        return SuccessResponse(ingredientGroupId)
    }

    private fun createIngredient(request: UpdateIngredientGroupRequest.UpdateIngredientRequest): Ingredient {
        return Ingredient(0, request.name, request.quantity)
    }

    fun delete(recipeId: Int, ingredientGroupId: Int): SuccessResponse {
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

    fun deleteIngredient(recipeId: Int, ingredientGroupId: Int, ingredientId: Int): SuccessResponse {
        recipeDao.findById(recipeId)?.let { recipe ->
            recipe.ingredientGroups.firstOrNull { ingredientGroup -> ingredientGroup.id == ingredientGroupId }
                ?.let { ingredientGroup ->
                    ingredientGroup.ingredients.firstOrNull { ingredient -> ingredient.id == ingredientId }
                        ?.let { ingredient -> ingredientDao.delete(ingredient) }
                }
        }
        return SuccessResponse(ingredientId)
    }

    fun find(recipeId: Int, ingredientGroupId: Int): IngredientGroupResponse {
        return IngredientGroupResponse(
            ingredientGroupMapper.map(recipeDao.findById(recipeId)?.let { recipe ->
                recipe.ingredientGroups.firstOrNull { ingredientGroup -> ingredientGroup.id == ingredientGroupId }
            } ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_GROUP_NOT_FOUND))
        )
    }

}
package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteCategoryCommand(private val categoryDao: CategoryDao, private val recipeDao: RecipeDao) {

    fun execute(categoryId: Int): SuccessResponse {
        val category = categoryDao.findById(categoryId) ?: throw KitchenAssistantException(ErrorCode.CATEGORY_NOT_FOUND)
        if (category.deleted) {
            throw KitchenAssistantException(ErrorCode.CATEGORY_ALREADY_REMOVED)
        }
        category.deleted = true

        val recipes = recipeDao.findAll(RecipeCriteria(false, category.userId, category.id))
        recipes.forEach { it.category = null }
        recipeDao.save(recipes)

        categoryDao.save(category)
        return SuccessResponse(category.id)
    }

}
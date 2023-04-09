package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteCategoryCommand(private val categoryDao: CategoryDao, private val recipeDao: RecipeDao) {

    fun execute(categoryId: Int): SuccessResponse {
        categoryDao.findById(categoryId)?.let {
            val recipes = recipeDao.findAll(RecipeCriteria(false, it.userId, it.id))
            recipes.forEach { recipe -> recipe.category = null }
            recipeDao.save(recipes)
            categoryDao.delete(it)
        }
        return SuccessResponse(categoryId)
    }

}
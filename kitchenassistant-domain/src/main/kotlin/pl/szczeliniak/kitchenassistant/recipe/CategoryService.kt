package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.recipe.dto.request.NewCategoryRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.request.UpdateCategoryRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.response.CategoriesResponse
import pl.szczeliniak.kitchenassistant.recipe.mapper.CategoryMapper
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.db.UserDao

open class CategoryService(
    private val recipeDao: RecipeDao,
    private val categoryDao: CategoryDao,
    private val userDao: UserDao,
    private val categoryMapper: CategoryMapper,
    private val requestContext: RequestContext
) {

    fun add(request: NewCategoryRequest): SuccessResponse {
        return SuccessResponse(categoryDao.save(createCategory(request)).id)
    }

    private fun createCategory(request: NewCategoryRequest): Category {
        return Category(
            0,
            request.name,
            userDao.findById(requestContext.userId())
                ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND),
            request.sequence
        )
    }

    fun getAll(criteria: CategoryCriteria): CategoriesResponse {
        return CategoriesResponse(categoryDao.findAll(criteria).map { categoryMapper.map(it) }.toSet())
    }

    fun delete(categoryId: Int): SuccessResponse {
        categoryDao.findById(categoryId)?.let {
            val recipes = recipeDao.findAll(RecipeCriteria(categoryId = categoryId))
            recipes.forEach { recipe -> recipe.category = null }
            recipeDao.save(recipes)
            categoryDao.delete(it)
        }
        return SuccessResponse(categoryId)
    }

    fun update(categoryId: Int, request: UpdateCategoryRequest): SuccessResponse {
        val category = categoryDao.findById(categoryId) ?: throw KitchenAssistantException(ErrorCode.CATEGORY_NOT_FOUND)
        category.name = request.name
        category.sequence = request.sequence
        return SuccessResponse(categoryDao.save(category).id)
    }

}
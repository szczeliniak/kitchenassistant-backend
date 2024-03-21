package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.Category
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.dto.request.NewCategoryRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.request.UpdateCategoryRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.response.CategoriesResponse
import pl.szczeliniak.kitchenassistant.shared.BaseService
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import java.time.ZonedDateTime

open class CategoryService(
    private val recipeDao: RecipeDao,
    private val categoryDao: CategoryDao,
    private val userDao: UserDao,
    private val categoryMapper: CategoryMapper,
    requestContext: RequestContext
) : BaseService(requestContext) {

    fun add(request: NewCategoryRequest): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        return SuccessResponse(categoryDao.save(createCategory(request)).id)
    }

    private fun createCategory(request: NewCategoryRequest): Category {
        return Category(
            0,
            request.name,
            userDao.findById(requestContext.userId())
                ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND)
        )
    }

    fun getAll(): CategoriesResponse {
        requireTokenType(TokenType.ACCESS)
        return CategoriesResponse(categoryDao.findAll(requestContext.userId()).map { categoryMapper.map(it) }.toList())
    }

    fun delete(categoryId: Int): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val userId = requestContext.userId()
        val category = categoryDao.findById(categoryId, userId) ?: throw KitchenAssistantException(ErrorCode.CATEGORY_NOT_FOUND)

        val recipes = recipeDao.findAll(RecipeCriteria(categoryId = categoryId), userId)
        recipes.forEach { recipe -> recipe.category = null }
        recipeDao.save(recipes)
        categoryDao.delete(category)
        return SuccessResponse(categoryId)
    }

    fun update(categoryId: Int, request: UpdateCategoryRequest): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val category = categoryDao.findById(categoryId, requestContext.userId()) ?: throw KitchenAssistantException(ErrorCode.CATEGORY_NOT_FOUND)
        category.name = request.name
        category.modifiedAt = ZonedDateTime.now()
        return SuccessResponse(categoryDao.save(category).id)
    }

}
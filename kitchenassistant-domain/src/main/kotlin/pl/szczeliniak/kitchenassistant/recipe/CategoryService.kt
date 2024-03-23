package pl.szczeliniak.kitchenassistant.recipe

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

open class CategoryService(
    private val recipeDao: RecipeDao,
    private val categoryDao: CategoryDao,
    private val userDao: UserDao,
    private val recipeMapper: RecipeMapper,
    requestContext: RequestContext
) : BaseService(requestContext) {

    fun add(request: NewCategoryRequest): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        return SuccessResponse(
            categoryDao.save(
                recipeMapper.category(
                    0, request, userDao.findById(requestContext.userId())
                        ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND)
                )
            ).id
        )
    }

    fun getAll(): CategoriesResponse {
        requireTokenType(TokenType.ACCESS)
        return CategoriesResponse(categoryDao.findAll(requestContext.userId()).map { recipeMapper.mapCategory(it) }.toList())
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
        recipeMapper.category(category, request)
        return SuccessResponse(categoryDao.save(category).id)
    }

}
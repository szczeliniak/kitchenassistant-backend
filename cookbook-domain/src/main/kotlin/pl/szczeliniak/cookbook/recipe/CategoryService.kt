package pl.szczeliniak.cookbook.recipe

import pl.szczeliniak.cookbook.recipe.db.CategoryDao
import pl.szczeliniak.cookbook.recipe.db.RecipeCriteria
import pl.szczeliniak.cookbook.recipe.db.RecipeDao
import pl.szczeliniak.cookbook.recipe.dto.request.NewCategoryRequest
import pl.szczeliniak.cookbook.recipe.dto.request.UpdateCategoryRequest
import pl.szczeliniak.cookbook.recipe.dto.response.CategoriesResponse
import pl.szczeliniak.cookbook.shared.BaseService
import pl.szczeliniak.cookbook.shared.ErrorCode
import pl.szczeliniak.cookbook.shared.CookBookException
import pl.szczeliniak.cookbook.shared.RequestContext
import pl.szczeliniak.cookbook.shared.TokenType
import pl.szczeliniak.cookbook.shared.dtos.SuccessResponse
import pl.szczeliniak.cookbook.user.db.UserDao

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
                        ?: throw CookBookException(ErrorCode.USER_NOT_FOUND)
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
        val category = categoryDao.findById(categoryId, userId) ?: throw CookBookException(ErrorCode.CATEGORY_NOT_FOUND)

        val recipes = recipeDao.findAll(RecipeCriteria(categoryId = categoryId), userId)
        recipes.forEach { recipe -> recipe.category = null }
        recipeDao.save(recipes)
        categoryDao.delete(category)
        return SuccessResponse(categoryId)
    }

    fun update(categoryId: Int, request: UpdateCategoryRequest): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val category = categoryDao.findById(categoryId, requestContext.userId()) ?: throw CookBookException(ErrorCode.CATEGORY_NOT_FOUND)
        recipeMapper.category(category, request)
        return SuccessResponse(categoryDao.save(category).id)
    }

}
package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.AuthorDao
import pl.szczeliniak.kitchenassistant.recipe.db.Category
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.recipe.db.Ingredient
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroup
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.db.Step
import pl.szczeliniak.kitchenassistant.recipe.db.StepGroup
import pl.szczeliniak.kitchenassistant.recipe.db.Tag
import pl.szczeliniak.kitchenassistant.recipe.db.TagDao
import pl.szczeliniak.kitchenassistant.recipe.dto.request.NewRecipeRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.request.UpdateRecipeRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.response.RecipeResponse
import pl.szczeliniak.kitchenassistant.recipe.dto.response.RecipesResponse
import pl.szczeliniak.kitchenassistant.shared.BaseService
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.PaginationUtils
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType
import pl.szczeliniak.kitchenassistant.shared.dtos.Page
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import java.time.ZonedDateTime

open class RecipeService(
    private val recipeDao: RecipeDao,
    private val authorDao: AuthorDao,
    private val tagDao: TagDao,
    private val categoryDao: CategoryDao,
    private val userDao: UserDao,
    private val recipeMapper: RecipeMapper,
    requestContext: RequestContext
) : BaseService(requestContext) {

    open fun findById(recipeId: Int): RecipeResponse {
        requireTokenType(TokenType.ACCESS)
        return RecipeResponse(recipeMapper.mapDetails(recipeDao.findById(recipeId, requestContext.userId()) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)))
    }

    fun findAll(page: Long?, limit: Int?, favorite: Boolean? = null, categoryId: Int? = null, search: String? = null, tag: String? = null): RecipesResponse {
        requireTokenType(TokenType.ACCESS)
        val userId = requestContext.userId()
        val currentPage = PaginationUtils.calculatePageNumber(page)
        val currentLimit = PaginationUtils.calculateLimit(limit)
        val offset = PaginationUtils.calculateOffset(currentPage, currentLimit)
        val criteria = RecipeCriteria(favorite, categoryId, search, tag)
        val totalNumberOfPages = PaginationUtils.calculateNumberOfPages(currentLimit, recipeDao.count(criteria, userId))
        return RecipesResponse(
            Page(
                currentPage,
                currentLimit,
                totalNumberOfPages,
                recipeDao.findAll(criteria, userId, offset, currentLimit).map { recipeMapper.map(it) })
        )
    }

    fun add(request: NewRecipeRequest): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        return SuccessResponse(recipeDao.save(createRecipe(request)).id)
    }

    private fun createRecipe(request: NewRecipeRequest): Recipe {
        val userId = requestContext.userId()
        return Recipe(0,
            user = userDao.findById(userId) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND),
            name = request.name,
            author = request.author?.trim()?.let {
                authorDao.findByName(it, userId) ?: authorDao.save(createAuthor(it, userId))
            },
            source = request.source,
            category = request.categoryId?.let {
                categoryDao.findById(it, userId) ?: throw KitchenAssistantException(ErrorCode.CATEGORY_NOT_FOUND)
            },
            description = request.description,
            ingredientGroups = request.ingredientGroups.map { createIngredientGroup(it) }.toMutableSet(),
            stepGroups = request.stepGroups.map { createStepGroup(it) }.toMutableSet(),
            tags = request.tags.map { it.trim() }
                .map { tagDao.findByName(it, userId) ?: createTag(it, userId) }
                .toMutableSet()
        )
    }

    private fun createTag(name: String, userId: Int): Tag {
        return Tag(0, name, userDao.findById(userId) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND))
    }

    private fun createAuthor(name: String, userId: Int): Author {
        return Author(0, name, userDao.findById(userId) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND))
    }

    private fun createStepGroup(request: NewRecipeRequest.StepGroup): StepGroup {
        return StepGroup(
            0,
            request.name,
            request.steps.map { createStep(it) }.toMutableSet()
        )
    }

    private fun createStep(request: NewRecipeRequest.StepGroup.Step): Step {
        return Step(0, request.description, request.sequence)
    }

    private fun createIngredientGroup(request: NewRecipeRequest.IngredientGroup): IngredientGroup {
        return IngredientGroup(
            0,
            request.name,
            request.ingredients.map { createIngredient(it) }.toMutableSet()
        )
    }

    private fun createIngredient(request: NewRecipeRequest.IngredientGroup.Ingredient): Ingredient {
        return Ingredient(0, request.name, request.quantity)
    }

    private fun createAuthor(name: String, user: User): Author {
        return Author(0, name, user)
    }

    fun update(recipeId: Int, request: UpdateRecipeRequest): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val userId = requestContext.userId()
        val recipe = recipeDao.findById(recipeId, userId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        recipe.name = request.name
        recipe.description = request.description
        recipe.category = getCategory(recipe.category, request.categoryId, recipe.user.id)
        recipe.source = request.source
        recipe.author = request.author?.trim()?.let {
            authorDao.findByName(it, recipe.user.id) ?: authorDao.save(
                createAuthor(it, recipe.user)
            )
        }
        recipe.tags = request.tags.map { it.trim() }.map {
            recipe.tags.firstOrNull { tag -> it == tag.name } ?: tagDao.findByName(it, recipe.user.id) ?: tagDao.save(
                createTag(it, recipe.user)
            )
        }.toMutableSet()
        recipe.modifiedAt = ZonedDateTime.now()
        return SuccessResponse(recipeDao.save(recipe).id)
    }

    private fun createTag(name: String, user: User): Tag {
        return Tag(0, name, user)
    }

    fun delete(recipeId: Int): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val recipe = recipeDao.findById(recipeId, requestContext.userId()) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        recipeDao.delete(recipe)
        return SuccessResponse(recipeId)
    }

    fun markAsFavorite(recipeId: Int, isFavorite: Boolean): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val recipe = recipeDao.findById(recipeId, requestContext.userId()) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        recipe.favorite = isFavorite
        recipe.modifiedAt = ZonedDateTime.now()
        return SuccessResponse(recipeDao.save(recipe).id)
    }

    private fun getCategory(category: Category?, categoryId: Int?, userId: Int): Category? {
        if (category != null && categoryId != null && category.id == categoryId) {
            return category
        }
        return categoryId?.let {
            categoryDao.findById(it, userId) ?: throw KitchenAssistantException(ErrorCode.CATEGORY_NOT_FOUND)
        }
    }

}
package pl.szczeliniak.cookbook.recipe

import pl.szczeliniak.cookbook.recipe.db.Author
import pl.szczeliniak.cookbook.recipe.db.AuthorDao
import pl.szczeliniak.cookbook.recipe.db.Category
import pl.szczeliniak.cookbook.recipe.db.CategoryDao
import pl.szczeliniak.cookbook.recipe.db.Ingredient
import pl.szczeliniak.cookbook.recipe.db.IngredientDao
import pl.szczeliniak.cookbook.recipe.db.IngredientGroup
import pl.szczeliniak.cookbook.recipe.db.IngredientGroupDao
import pl.szczeliniak.cookbook.recipe.db.Recipe
import pl.szczeliniak.cookbook.recipe.db.RecipeCriteria
import pl.szczeliniak.cookbook.recipe.db.RecipeDao
import pl.szczeliniak.cookbook.recipe.db.Step
import pl.szczeliniak.cookbook.recipe.db.StepDao
import pl.szczeliniak.cookbook.recipe.db.StepGroup
import pl.szczeliniak.cookbook.recipe.db.StepGroupDao
import pl.szczeliniak.cookbook.recipe.db.Tag
import pl.szczeliniak.cookbook.recipe.db.TagDao
import pl.szczeliniak.cookbook.recipe.dto.request.NewRecipeRequest
import pl.szczeliniak.cookbook.recipe.dto.request.UpdateRecipeRequest
import pl.szczeliniak.cookbook.recipe.dto.response.RecipeResponse
import pl.szczeliniak.cookbook.recipe.dto.response.RecipesResponse
import pl.szczeliniak.cookbook.shared.BaseService
import pl.szczeliniak.cookbook.shared.ErrorCode
import pl.szczeliniak.cookbook.shared.CookBookException
import pl.szczeliniak.cookbook.shared.PaginationUtils
import pl.szczeliniak.cookbook.shared.RequestContext
import pl.szczeliniak.cookbook.shared.TokenType
import pl.szczeliniak.cookbook.shared.dtos.Page
import pl.szczeliniak.cookbook.shared.dtos.SuccessResponse
import pl.szczeliniak.cookbook.user.db.User
import pl.szczeliniak.cookbook.user.db.UserDao

open class RecipeService(
    private val recipeDao: RecipeDao,
    private val authorDao: AuthorDao,
    private val tagDao: TagDao,
    private val categoryDao: CategoryDao,
    private val userDao: UserDao,
    private val recipeMapper: RecipeMapper,
    private val ingredientGroupDao: IngredientGroupDao,
    private val ingredientDao: IngredientDao,
    private val stepGroupDao: StepGroupDao,
    private val stepDao: StepDao,
    requestContext: RequestContext
) : BaseService(requestContext) {

    open fun findById(recipeId: Int): RecipeResponse {
        requireTokenType(TokenType.ACCESS)
        return RecipeResponse(recipeMapper.mapDetails(recipeDao.findById(recipeId, null, requestContext.userId()) ?: throw CookBookException(ErrorCode.RECIPE_NOT_FOUND)))
    }

    fun findAll(page: Long?, limit: Int?, favorite: Boolean? = null, categoryId: Int? = null, search: String? = null, tag: String? = null): RecipesResponse {
        requireTokenType(TokenType.ACCESS)
        val userId = requestContext.userId()
        val currentPage = PaginationUtils.calculatePageNumber(page)
        val currentLimit = PaginationUtils.calculateLimit(limit)
        val offset = PaginationUtils.calculateOffset(currentPage, currentLimit)
        val criteria = RecipeCriteria(favorite, categoryId, search, tag, false)
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
            user = userDao.findById(userId) ?: throw CookBookException(ErrorCode.USER_NOT_FOUND),
            name = request.name,
            author = request.author?.trim()?.let {
                authorDao.findByName(it, userId) ?: authorDao.save(createAuthor(it, userId))
            },
            source = request.source,
            category = request.categoryId?.let {
                categoryDao.findById(it, userId) ?: throw CookBookException(ErrorCode.CATEGORY_NOT_FOUND)
            },
            description = request.description,
            ingredientGroups = request.ingredientGroups.map { createIngredientGroup(it) }.toMutableList(),
            stepGroups = request.stepGroups.map { createStepGroup(it) }.toMutableList(),
            tags = request.tags.map { it.trim() }
                .map { tagDao.findByName(it, userId) ?: createTag(it, userId) }
                .toMutableList()
        )
    }

    private fun createTag(name: String, userId: Int): Tag {
        return Tag(0, name, userDao.findById(userId) ?: throw CookBookException(ErrorCode.USER_NOT_FOUND))
    }

    private fun createAuthor(name: String, userId: Int): Author {
        return Author(0, name, userDao.findById(userId) ?: throw CookBookException(ErrorCode.USER_NOT_FOUND))
    }

    private fun createStepGroup(request: NewRecipeRequest.StepGroup): StepGroup {
        return StepGroup(
            0,
            request.name,
            request.steps.map { createStep(it) }.toMutableList()
        )
    }

    private fun createStep(request: NewRecipeRequest.StepGroup.Step): Step {
        return Step(0, request.description)
    }

    private fun createIngredientGroup(request: NewRecipeRequest.IngredientGroup): IngredientGroup {
        return IngredientGroup(
            0,
            request.name,
            request.ingredients.map { createIngredient(it) }.toMutableList()
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
        val recipe = recipeDao.findById(recipeId, false, userId) ?: throw CookBookException(ErrorCode.RECIPE_NOT_FOUND)
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
        }.toMutableList()

        recipe.ingredientGroups.forEach { ingredientGroup ->
            ingredientGroup.ingredients.forEach { ingredient -> ingredientDao.delete(ingredient) }
            ingredientGroupDao.delete(ingredientGroup)
        }
        recipe.ingredientGroups.clear()
        request.ingredientGroups.forEach { recipe.ingredientGroups.add(createIngredientGroup(it)) }

        recipe.stepGroups.forEach { stepGroup ->
            stepGroup.steps.forEach { ingredient -> stepDao.delete(ingredient) }
            stepGroupDao.delete(stepGroup)
        }
        recipe.stepGroups.clear()
        request.stepGroups.forEach { recipe.stepGroups.add(createStepGroup(it)) }

        return SuccessResponse(recipeDao.save(recipe).id)
    }

    private fun createIngredientGroup(request: UpdateRecipeRequest.IngredientGroup): IngredientGroup {
        return IngredientGroup(
            0,
            request.name,
            request.ingredients.map { createIngredient(it) }.toMutableList()
        )
    }

    private fun createIngredient(request: UpdateRecipeRequest.IngredientGroup.Ingredient): Ingredient {
        return Ingredient(0, request.name, request.quantity)
    }

    private fun createStepGroup(request: UpdateRecipeRequest.StepGroup): StepGroup {
        return StepGroup(
            0,
            request.name,
            request.steps.map { createStep(it) }.toMutableList()
        )
    }

    private fun createStep(request: UpdateRecipeRequest.StepGroup.Step): Step {
        return Step(0, request.description)
    }

    private fun createTag(name: String, user: User): Tag {
        return Tag(0, name, user)
    }

    fun archive(recipeId: Int): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val recipe = recipeDao.findById(recipeId, false, requestContext.userId()) ?: throw CookBookException(ErrorCode.RECIPE_NOT_FOUND)
        recipe.archived = true
        recipeDao.save(recipe)
        return SuccessResponse(recipeId)
    }

    fun markAsFavorite(recipeId: Int, isFavorite: Boolean): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val recipe = recipeDao.findById(recipeId, false, requestContext.userId()) ?: throw CookBookException(ErrorCode.RECIPE_NOT_FOUND)
        recipe.favorite = isFavorite
        return SuccessResponse(recipeDao.save(recipe).id)
    }

    private fun getCategory(category: Category?, categoryId: Int?, userId: Int): Category? {
        if (category != null && categoryId != null && category.id == categoryId) {
            return category
        }
        return categoryId?.let {
            categoryDao.findById(it, userId) ?: throw CookBookException(ErrorCode.CATEGORY_NOT_FOUND)
        }
    }

}
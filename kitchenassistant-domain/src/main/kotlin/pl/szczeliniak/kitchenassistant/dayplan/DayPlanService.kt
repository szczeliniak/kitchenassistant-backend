package pl.szczeliniak.kitchenassistant.dayplan

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.db.IngredientGroupSnapshotDao
import pl.szczeliniak.kitchenassistant.dayplan.db.IngredientSnapshotDao
import pl.szczeliniak.kitchenassistant.dayplan.db.RecipeSnapshotDao
import pl.szczeliniak.kitchenassistant.dayplan.db.Sort
import pl.szczeliniak.kitchenassistant.dayplan.db.StepSnapshotDao
import pl.szczeliniak.kitchenassistant.dayplan.dto.request.AddRecipeToDayPlanRequest
import pl.szczeliniak.kitchenassistant.dayplan.dto.request.UpdateDayPlanRequest
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlansResponse
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.BaseService
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.PaginationUtils
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType
import pl.szczeliniak.kitchenassistant.shared.dtos.Page
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import java.time.LocalDate
import java.time.ZonedDateTime

open class DayPlanService(
    private val dayPlanMapper: DayPlanMapper,
    private val recipeSnapshotMapper: RecipeSnapshotMapper,
    private val dayPlanDao: DayPlanDao,
    private val recipeDao: RecipeDao,
    requestContext: RequestContext,
    private val recipeSnapshotDao: RecipeSnapshotDao,
    private val ingredientGroupSnapshotDao: IngredientGroupSnapshotDao,
    private val stepSnapshotDao: StepSnapshotDao,
    private val ingredientSnapshotDao: IngredientSnapshotDao
) : BaseService(requestContext) {

    fun findByDate(date: LocalDate): DayPlanResponse {
        requireTokenType(TokenType.ACCESS)
        val userId = requestContext.userId()
        val dayPlan = dayPlanDao.findByDate(date, userId) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        val recipes = dayPlan.recipes.mapNotNull { recipe ->
            recipeDao.findById(recipe.originalRecipeId, userId)?.let { originalRecipe ->
                return@mapNotNull dayPlanMapper.map(recipe, originalRecipe.author?.name, originalRecipe.category?.name)
            }
        }
        return DayPlanResponse(
            dayPlanMapper.mapDetails(dayPlan, recipes)
        )
    }

    fun findAll(page: Long?, limit: Int?, sort: Sort, recipeId: Int? = null, since: LocalDate? = null, to: LocalDate? = null): DayPlansResponse {
        requireTokenType(TokenType.ACCESS)
        val userId = requestContext.userId()
        val currentPage = PaginationUtils.calculatePageNumber(page)
        val currentLimit = PaginationUtils.calculateLimit(limit)
        val offset = PaginationUtils.calculateOffset(currentPage, currentLimit)
        val criteria = DayPlanCriteria(recipeId, since, to)
        val result = dayPlanDao.findAll(criteria, userId, sort, offset, limit)
        val totalNumberOfPages =
            PaginationUtils.calculateNumberOfPages(currentLimit, dayPlanDao.count(criteria, userId))
        return DayPlansResponse(
            Page(
                currentPage,
                currentLimit,
                totalNumberOfPages,
                result.map { dayPlanMapper.map(it) })
        )
    }

    fun delete(date: LocalDate): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val dayPlan = dayPlanDao.findByDate(date, requestContext.userId()) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        dayPlanDao.delete(dayPlan)
        return SuccessResponse(dayPlan.id)
    }

    fun update(date: LocalDate, request: UpdateDayPlanRequest): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val dayPlan = dayPlanDao.findByDate(date, requestContext.userId()) ?: throw KitchenAssistantException(
            ErrorCode.DAY_PLAN_NOT_FOUND
        )

        dayPlanDao.findByDate(request.date, requestContext.userId())?.let {
            throw KitchenAssistantException(ErrorCode.DAY_PLAN_ALREADY_EXISTS)
        }
        dayPlan.date = request.date
        dayPlan.modifiedAt = ZonedDateTime.now()
        return SuccessResponse(dayPlanDao.save(dayPlan).id)
    }

    fun addRecipe(request: AddRecipeToDayPlanRequest): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val userId = requestContext.userId()

        if (request.date < LocalDate.now()) {
            throw KitchenAssistantException(ErrorCode.DAY_PLAN_DATE_TOO_OLD)
        }

        val dayPlan = dayPlanDao.findAll(DayPlanCriteria(null, request.date, request.date), userId).firstOrNull()
            ?: createDayPlan(request, userId)

        if (dayPlan.recipes.map { it.originalRecipeId }.contains(request.recipeId)) {
            throw KitchenAssistantException(ErrorCode.RECIPE_ALREADY_ADDED_TO_DAY_PLAN)
        }

        val recipeSnapshot = recipeSnapshotMapper.map(
            recipeDao.findById(request.recipeId, userId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        )

        recipeSnapshot.ingredientGroups.forEach { ingredientGroup ->
            ingredientGroup.ingredients.forEach { ingredient ->
                ingredientSnapshotDao.save(ingredient)
            }
            ingredientGroupSnapshotDao.save(ingredientGroup)
        }

        recipeSnapshot.steps.forEach {
            stepSnapshotDao.save(it)
        }

        dayPlan.recipes.add(recipeSnapshotDao.save(recipeSnapshot))

        return SuccessResponse(dayPlanDao.save(dayPlan).id)
    }

    private fun createDayPlan(request: AddRecipeToDayPlanRequest, userId: Int): DayPlan {
        return DayPlan(userId = userId, date = request.date)
    }

    fun deleteRecipe(date: LocalDate, recipeId: Int): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val userId = requestContext.userId()
        val dayPlan = dayPlanDao.findByDate(date, userId) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        val recipe = dayPlan.recipes.firstOrNull { it.id == recipeId } ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        dayPlan.recipes.remove(recipe)
        if (dayPlan.recipes.isEmpty()) {
            dayPlanDao.delete(dayPlan)
        } else {
            dayPlanDao.save(dayPlan)
        }
        return SuccessResponse(dayPlan.id)
    }

    fun checkIngredient(date: LocalDate, recipeId: Int, ingredientGroupId: Int, ingredientId: Int, checked: Boolean): SuccessResponse {
        requireTokenType(TokenType.ACCESS)
        val dayPlan = dayPlanDao.findByDate(date, requestContext.userId()) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        val ingredient = dayPlan.recipes.first { it.id == recipeId }
            .ingredientGroups.first { it.id == ingredientGroupId }
            .ingredients.first { it.id == ingredientId }
        ingredient.checked = checked
        ingredient.modifiedAt = ZonedDateTime.now()
        ingredientSnapshotDao.save(ingredient)
        return SuccessResponse(dayPlan.id)
    }

}
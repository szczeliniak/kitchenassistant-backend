package pl.szczeliniak.kitchenassistant.dayplan

import pl.szczeliniak.kitchenassistant.dayplan.db.*
import pl.szczeliniak.kitchenassistant.dayplan.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.dayplan.dto.request.AddRecipeToDayPlanRequest
import pl.szczeliniak.kitchenassistant.dayplan.dto.request.UpdateDayPlanRequest
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlansResponse
import pl.szczeliniak.kitchenassistant.dayplan.mapper.DayPlanMapper
import pl.szczeliniak.kitchenassistant.dayplan.mapper.RecipeSnapshotMapper
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.PaginationUtils
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.dtos.Page
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import java.time.LocalDate
import java.time.ZonedDateTime

open class DayPlanService(
    private val dayPlanMapper: DayPlanMapper,
    private val recipeSnapshotMapper: RecipeSnapshotMapper,
    private val dayPlanDao: DayPlanDao,
    private val recipeDao: RecipeDao,
    private val requestContext: RequestContext,
    private val recipeSnapshotDao: RecipeSnapshotDao,
    private val ingredientGroupSnapshotDao: IngredientGroupSnapshotDao,
    private val stepSnapshotDao: StepSnapshotDao,
    private val ingredientSnapshotDao: IngredientSnapshotDao
) {

    fun findById(id: Int): DayPlanResponse {
        return DayPlanResponse(
            dayPlanMapper.mapDetails(
                dayPlanDao.findById(id, requestContext.requireUserId())
                    ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
            )
        )
    }

    fun findAll(page: Long?, limit: Int?, criteria: DayPlanCriteria): DayPlansResponse {
        val userId = requestContext.requireUserId()

        val currentPage = PaginationUtils.calculatePageNumber(page)
        val currentLimit = PaginationUtils.calculateLimit(limit)
        val offset = PaginationUtils.calculateOffset(currentPage, currentLimit)
        val result = dayPlanDao.findAll(criteria, offset, limit, userId)
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

    fun delete(id: Int): SuccessResponse {
        val userId = requestContext.requireUserId()
        val dayPlan =
            dayPlanDao.findById(id, userId) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        dayPlanDao.delete(id, userId)
        return SuccessResponse(dayPlan.id)
    }

    fun update(id: Int, request: UpdateDayPlanRequest): SuccessResponse {
        val dayPlan = dayPlanDao.findById(id, requestContext.requireUserId()) ?: throw KitchenAssistantException(
            ErrorCode.DAY_PLAN_NOT_FOUND
        )

        dayPlanDao.findByDate(request.date, requestContext.requireUserId())?.let {
            throw KitchenAssistantException(ErrorCode.DAY_PLAN_ALREADY_EXISTS)
        }
        dayPlan.date = request.date

        return SuccessResponse(dayPlanDao.save(dayPlan).id)
    }

    fun addRecipe(request: AddRecipeToDayPlanRequest): SuccessResponse {
        val userId = requestContext.requireUserId()

        if (request.date < LocalDate.now()) {
            throw KitchenAssistantException(ErrorCode.DAY_PLAN_DATE_TOO_OLD)
        }

        val dayPlan =
            dayPlanDao.findAll(DayPlanCriteria(null, request.date, request.date), userId = userId).firstOrNull()
                ?: createDayPlan(request, userId)

        if (dayPlan.recipes.map { it.recipe.id }.contains(request.recipeId)) {
            throw KitchenAssistantException(ErrorCode.RECIPE_ALREADY_ADDED_TO_DAY_PLAN)
        }

        val snapshot = recipeSnapshotMapper.map(
            recipeDao.findById(request.recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        )

        snapshot.ingredientGroups.forEach { group ->
            group.ingredients.forEach { ingredient ->
                ingredientSnapshotDao.save(ingredient)
            }
            ingredientGroupSnapshotDao.save(group)
        }

        snapshot.steps.forEach {
            stepSnapshotDao.save(it)
        }

        dayPlan.recipes.add(recipeSnapshotDao.save(snapshot))

        return SuccessResponse(dayPlanDao.save(dayPlan).id)
    }

    private fun createDayPlan(request: AddRecipeToDayPlanRequest, userId: Int): DayPlan {
        return DayPlan(userId = userId, date = request.date)
    }

    fun deleteRecipe(id: Int, recipeId: Int): SuccessResponse {
        val userId = requestContext.requireUserId()
        dayPlanDao.findById(id, userId)?.let { dayPlan ->
            dayPlan.recipes.removeIf { it.id == recipeId }
            if (dayPlan.recipes.isEmpty()) {
                dayPlanDao.delete(dayPlan.id, userId)
            } else {
                dayPlanDao.save(dayPlan)
            }
            return SuccessResponse(dayPlan.id)
        } ?: run {
            throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        }
    }

    fun check(
        id: Int,
        recipeId: Int,
        ingredientGroupId: Int,
        ingredientId: Int,
        checked: Boolean
    ): SuccessResponse {
        val dayPlan = dayPlanDao.findById(id, requestContext.requireUserId()) ?: throw KitchenAssistantException(
            ErrorCode.DAY_PLAN_NOT_FOUND
        )
        val ingredient =
            dayPlan.recipes.first { it.id == recipeId }.ingredientGroups.first { it.id == ingredientGroupId }.ingredients.first { it.id == ingredientId }
        ingredient.checked = checked
        ingredient.modifiedAt = ZonedDateTime.now()
        ingredientSnapshotDao.save(ingredient)

        return SuccessResponse(dayPlan.id)
    }

}
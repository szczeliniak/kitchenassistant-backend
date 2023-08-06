package pl.szczeliniak.kitchenassistant.dayplan

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.dayplan.dto.request.AddRecipeToDayPlanRequest
import pl.szczeliniak.kitchenassistant.dayplan.dto.request.UpdateDayPlanRequest
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlansResponse
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.PaginationUtils
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.dtos.Page
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import java.time.LocalDate

open class DayPlanService(
    private val dayPlanMapper: DayPlanMapper,
    private val dayPlanDao: DayPlanDao,
    private val recipeDao: RecipeDao,
    private val requestContext: RequestContext
) {

    fun findByDate(date: LocalDate): DayPlanResponse {
        return DayPlanResponse(
            dayPlanMapper.mapDetails(
                dayPlanDao.findByDate(date, requestContext.requireUserId())
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
        return DayPlansResponse(
            Page(
                currentPage,
                currentLimit,
                dayPlanDao.count(criteria, userId),
                result.map { dayPlanMapper.map(it) })
        )
    }

    fun delete(date: LocalDate): SuccessResponse {
        val userId = requestContext.requireUserId()
        val dayPlan =
            dayPlanDao.findByDate(date, userId) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        dayPlanDao.delete(date, userId)
        return SuccessResponse(dayPlan.id)
    }

    fun update(date: LocalDate, request: UpdateDayPlanRequest): SuccessResponse {
        val dayPlan = dayPlanDao.findByDate(date, requestContext.requireUserId()) ?: throw KitchenAssistantException(
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

        if (dayPlan.recipes.map { it.id }.contains(request.recipeId)) {
            throw KitchenAssistantException(ErrorCode.RECIPE_ALREADY_ADDED_TO_DAY_PLAN)
        }

        dayPlan.recipes.add(
            recipeDao.findById(request.recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        )

        return SuccessResponse(dayPlanDao.save(dayPlan).id)
    }

    private fun createDayPlan(request: AddRecipeToDayPlanRequest, userId: Int): DayPlan {
        return DayPlan(userId = userId, date = request.date)
    }

    fun deleteRecipe(date: LocalDate, recipeId: Int): SuccessResponse {
        val userId = requestContext.requireUserId()
        dayPlanDao.findByDate(date, userId)?.let { dayPlan ->
            dayPlan.recipes.removeIf { it.id == recipeId }
            if (dayPlan.recipes.isEmpty()) {
                dayPlanDao.delete(dayPlan.date, userId)
            } else {
                dayPlanDao.save(dayPlan)
            }
            return SuccessResponse(dayPlan.id)
        } ?: run {
            throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        }
    }

}
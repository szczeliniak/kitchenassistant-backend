package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.AddRecipeToDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.commands.factories.DayPlanFactory
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import java.time.LocalDate

class AddRecipeToDayPlanCommand(
    private val dayPlanDao: DayPlanDao,
    private val recipeDao: RecipeDao,
    private val dayPlanFactory: DayPlanFactory
) {

    fun execute(recipeId: Int, request: AddRecipeToDayPlanDto): SuccessResponse {
        if (request.date < LocalDate.now()) {
            throw KitchenAssistantException(ErrorCode.DAY_PLAN_DATE_TOO_OLD)
        }

        val dayPlan = dayPlanDao.findByDate(request.date) ?: dayPlanFactory.create(request)
        dayPlan.recipes.add(recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND))

        return SuccessResponse(dayPlanDao.save(dayPlan).id)
    }
}
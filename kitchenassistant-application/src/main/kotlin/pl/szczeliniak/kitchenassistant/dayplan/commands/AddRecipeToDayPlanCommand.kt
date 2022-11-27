package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.AddRecipeToDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.commands.factories.DayPlanFactory
import pl.szczeliniak.kitchenassistant.recipe.RecipeFacade
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AddRecipeToDayPlanCommand(
    private val dayPlanDao: DayPlanDao,
    private val recipeFacade: RecipeFacade,
    private val dayPlanFactory: DayPlanFactory
) {

    fun execute(recipeId: Int, request: AddRecipeToDayPlanDto): SuccessResponse {
        val dayPlan = dayPlanDao.findByDate(request.date) ?: dayPlanFactory.create(request)
        recipeFacade.getRecipe(recipeId)
        dayPlan.recipeIds.add(recipeId)
        return SuccessResponse(dayPlanDao.save(dayPlan).id)
    }
}
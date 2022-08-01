package pl.szczeliniak.kitchenassistant.dayplan

import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.NewDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.UpdateDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanRecipesResponse
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlansResponse
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

interface DayPlanFacade {

    fun getDayPlan(id: Int): DayPlanResponse
    fun getDayPlans(page: Long?, limit: Int?, criteria: DayPlanCriteria): DayPlansResponse
    fun addDayPlan(dto: NewDayPlanDto): SuccessResponse
    fun updateDayPlan(id: Int, dto: UpdateDayPlanDto): SuccessResponse
    fun deleteDayPlan(id: Int): SuccessResponse
    fun archiveDayPlan(id: Int, archive: Boolean): SuccessResponse
    fun addRecipeToDayPlan(id: Int, recipeId: Int): SuccessResponse
    fun deleteRecipeFromDayPlan(id: Int, recipeId: Int): SuccessResponse
    fun deassignRecipeFromAllDayPlans(recipeId: Int): SuccessResponse
    fun triggerArchiving()
    fun getRecipesByDayPlanId(id: Int): DayPlanRecipesResponse

}
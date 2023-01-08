package pl.szczeliniak.kitchenassistant.dayplan

import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.AddRecipeToDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.UpdateDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlansResponse
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

interface DayPlanFacade {

    fun findById(id: Int): DayPlanResponse
    fun findAll(page: Long?, limit: Int?, criteria: DayPlanCriteria): DayPlansResponse
    fun delete(id: Int): SuccessResponse
    fun archive(id: Int, archive: Boolean): SuccessResponse
    fun update(id: Int, request: UpdateDayPlanDto): SuccessResponse
    fun addRecipe(recipeId: Int, request: AddRecipeToDayPlanDto): SuccessResponse
    fun deleteRecipe(id: Int, recipeId: Int): SuccessResponse
    fun deassignRecipes(recipeId: Int): SuccessResponse
    fun triggerArchiving()

}
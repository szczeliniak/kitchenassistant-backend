package pl.szczeliniak.kitchenassistant.dayplan

import pl.szczeliniak.kitchenassistant.dayplan.commands.*
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.AddRecipeToDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlanQuery
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlansQuery
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetRecipesByDayPlanIdQuery
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanRecipesResponse
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlansResponse
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

open class DayPlanFacadeImpl(
    private val getDayPlanQuery: GetDayPlanQuery,
    private val getDayPlansQuery: GetDayPlansQuery,
    private val addRecipeToDayPlanCommand: AddRecipeToDayPlanCommand,
    private val deleteRecipeFromDayPlanCommand: DeleteRecipeFromDayPlanCommand,
    private val deleteDayPlanCommand: DeleteDayPlanCommand,
    private val archiveDayPlanCommand: ArchiveDayPlanCommand,
    private val deassignRecipesFromDayPlansCommand: DeassignRecipesFromDayPlansCommand,
    private val archiveDayPlansCommand: ArchiveDayPlansCommand,
    private val getRecipesByDayPlanIdQuery: GetRecipesByDayPlanIdQuery
) : DayPlanFacade {

    override fun getDayPlan(id: Int): DayPlanResponse {
        return getDayPlanQuery.execute(id)
    }

    override fun getDayPlans(page: Long?, limit: Int?, criteria: DayPlanCriteria): DayPlansResponse {
        return getDayPlansQuery.execute(page, limit, criteria)
    }

    override fun deleteDayPlan(id: Int): SuccessResponse {
        return deleteDayPlanCommand.execute(id)
    }

    override fun archiveDayPlan(id: Int, archive: Boolean): SuccessResponse {
        return archiveDayPlanCommand.execute(id, archive)
    }

    override fun addRecipeToDayPlan(recipeId: Int, request: AddRecipeToDayPlanDto): SuccessResponse {
        return addRecipeToDayPlanCommand.execute(recipeId, request)
    }

    override fun deleteRecipeFromDayPlan(id: Int, recipeId: Int): SuccessResponse {
        return deleteRecipeFromDayPlanCommand.execute(id, recipeId)
    }

    override fun deassignRecipeFromAllDayPlans(recipeId: Int): SuccessResponse {
        return deassignRecipesFromDayPlansCommand.execute(recipeId)
    }

    override fun triggerArchiving() {
        archiveDayPlansCommand.execute()
    }

    override fun getRecipesByDayPlanId(id: Int): DayPlanRecipesResponse {
        return getRecipesByDayPlanIdQuery.execute(id)
    }

}
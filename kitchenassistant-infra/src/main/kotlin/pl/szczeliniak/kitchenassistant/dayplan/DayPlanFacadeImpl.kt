package pl.szczeliniak.kitchenassistant.dayplan

import pl.szczeliniak.kitchenassistant.dayplan.commands.*
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.NewDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.UpdateDayPlanDto
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
    private val addDayPlanCommand: AddDayPlanCommand,
    private val updateDayPlanCommand: UpdateDayPlanCommand,
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

    override fun addDayPlan(dto: NewDayPlanDto): SuccessResponse {
        return addDayPlanCommand.execute(dto)
    }

    override fun updateDayPlan(id: Int, dto: UpdateDayPlanDto): SuccessResponse {
        return updateDayPlanCommand.execute(id, dto)
    }

    override fun deleteDayPlan(id: Int): SuccessResponse {
        return deleteDayPlanCommand.execute(id)
    }

    override fun archiveDayPlan(id: Int, archive: Boolean): SuccessResponse {
        return archiveDayPlanCommand.execute(id, archive)
    }

    override fun addRecipeToDayPlan(id: Int, recipeId: Int): SuccessResponse {
        return addRecipeToDayPlanCommand.execute(id, recipeId)
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
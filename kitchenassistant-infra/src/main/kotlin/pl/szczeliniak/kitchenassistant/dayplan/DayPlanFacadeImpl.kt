package pl.szczeliniak.kitchenassistant.dayplan

import pl.szczeliniak.kitchenassistant.dayplan.commands.*
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.AddRecipeToDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.UpdateDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlanQuery
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlansQuery
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
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
    private val updateDayPlanCommand: UpdateDayPlanCommand,
    private val deassignRecipesFromDayPlansCommand: DeassignRecipesFromDayPlansCommand,
    private val archiveDayPlansCommand: ArchiveDayPlansCommand
) : DayPlanFacade {

    override fun findById(id: Int): DayPlanResponse {
        return getDayPlanQuery.execute(id)
    }

    override fun findAll(page: Long?, limit: Int?, criteria: DayPlanCriteria): DayPlansResponse {
        return getDayPlansQuery.execute(page, limit, criteria)
    }

    override fun delete(id: Int): SuccessResponse {
        return deleteDayPlanCommand.execute(id)
    }

    override fun archive(id: Int, archive: Boolean): SuccessResponse {
        return archiveDayPlanCommand.execute(id, archive)
    }

    override fun update(id: Int, request: UpdateDayPlanDto): SuccessResponse {
        return updateDayPlanCommand.execute(id, request)
    }

    override fun addRecipe(recipeId: Int, request: AddRecipeToDayPlanDto): SuccessResponse {
        return addRecipeToDayPlanCommand.execute(recipeId, request)
    }

    override fun deleteRecipe(id: Int, recipeId: Int): SuccessResponse {
        return deleteRecipeFromDayPlanCommand.execute(id, recipeId)
    }

    override fun deassignRecipes(recipeId: Int): SuccessResponse {
        return deassignRecipesFromDayPlansCommand.execute(recipeId)
    }

    override fun triggerArchiving() {
        archiveDayPlansCommand.execute()
    }

}
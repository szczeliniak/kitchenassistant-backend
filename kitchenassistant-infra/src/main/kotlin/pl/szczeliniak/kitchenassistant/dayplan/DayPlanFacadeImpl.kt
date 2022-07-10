package pl.szczeliniak.kitchenassistant.dayplan

import pl.szczeliniak.kitchenassistant.dayplan.commands.*
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.NewDayPlanDto
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
    private val addDayPlanCommand: AddDayPlanCommand,
    private val updateDayPlanCommand: UpdateDayPlanCommand,
    private val addReceiptToDayPlanCommand: AddReceiptToDayPlanCommand,
    private val deleteReceiptFromDayPlanCommand: DeleteReceiptFromDayPlanCommand,
    private val deleteDayPlanCommand: DeleteDayPlanCommand,
    private val archiveDayPlanCommand: ArchiveDayPlanCommand,
    private val deassignReceiptsFromDayPlansCommand: DeassignReceiptsFromDayPlansCommand,
    private val archiveDayPlansCommand: ArchiveDayPlansCommand
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

    override fun addReceiptToDayPlan(id: Int, receiptId: Int): SuccessResponse {
        return addReceiptToDayPlanCommand.execute(id, receiptId)
    }

    override fun deleteReceiptFromDayPlan(id: Int, receiptId: Int): SuccessResponse {
        return deleteReceiptFromDayPlanCommand.execute(id, receiptId)
    }

    override fun deassignReceiptFromAllDayPlans(receiptId: Int): SuccessResponse {
        return deassignReceiptsFromDayPlansCommand.execute(receiptId)
    }

    override fun triggerArchiving() {
        archiveDayPlansCommand.execute()
    }

}
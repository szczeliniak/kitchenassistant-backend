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

open class DayPlanFacade(
    private val getDayPlanQuery: GetDayPlanQuery,
    private val getDayPlansQuery: GetDayPlansQuery,
    private val addDayPlanCommand: AddDayPlanCommand,
    private val updateDayPlanCommand: UpdateDayPlanCommand,
    private val addReceiptToDayPlanCommand: AddReceiptToDayPlanCommand,
    private val deleteReceiptFromDayPlanCommand: DeleteReceiptFromDayPlanCommand,
    private val deleteDayPlanCommand: DeleteDayPlanCommand,
    private val archiveDayPlanCommand: ArchiveDayPlanCommand
) {

    open fun getDayPlan(id: Int): DayPlanResponse {
        return getDayPlanQuery.execute(id)
    }

    open fun getDayPlans(page: Long?, limit: Int?, criteria: DayPlanCriteria): DayPlansResponse {
        return getDayPlansQuery.execute(page, limit, criteria)
    }

    open fun addDayPlan(dto: NewDayPlanDto): SuccessResponse {
        return addDayPlanCommand.execute(dto)
    }

    open fun updateDayPlan(id: Int, dto: UpdateDayPlanDto): SuccessResponse {
        return updateDayPlanCommand.execute(id, dto)
    }

    open fun deleteDayPlan(id: Int): SuccessResponse {
        return deleteDayPlanCommand.execute(id)
    }

    open fun archiveDayPlan(id: Int, archive: Boolean): SuccessResponse {
        return archiveDayPlanCommand.execute(id, archive)
    }

    open fun addReceiptToDayPlan(id: Int, receiptId: Int): SuccessResponse {
        return addReceiptToDayPlanCommand.execute(id, receiptId)
    }

    open fun deleteReceiptFromDayPlan(id: Int, receiptId: Int): SuccessResponse {
        return deleteReceiptFromDayPlanCommand.execute(id, receiptId)
    }

}
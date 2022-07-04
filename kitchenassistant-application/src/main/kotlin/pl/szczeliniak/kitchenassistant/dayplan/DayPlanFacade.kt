package pl.szczeliniak.kitchenassistant.dayplan

import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.NewDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.UpdateDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
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
    fun addReceiptToDayPlan(id: Int, receiptId: Int): SuccessResponse
    fun deleteReceiptFromDayPlan(id: Int, receiptId: Int): SuccessResponse
    fun deassignReceiptFromAllDayPlans(receiptId: Int): SuccessResponse

}
package pl.szczeliniak.kitchenassistant.dayplan.queries

import pl.szczeliniak.kitchenassistant.dayplan.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanReceiptsResponse
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptDetailsDto
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException

class GetReceiptsByDayPlanIdQuery(
    private val dayPlanDao: DayPlanDao,
    private val getReceiptQuery: GetReceiptQuery,
    private val dayPlanConverter: DayPlanConverter
) {

    fun execute(dayPlanId: Int): DayPlanReceiptsResponse {
        val dayPlan = dayPlanDao.findById(dayPlanId) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        return DayPlanReceiptsResponse(
            dayPlan.receiptIds.mapNotNull { getReceipt(it) }.map { dayPlanConverter.map(it) })
    }

    private fun getReceipt(it: Int): ReceiptDetailsDto? {
        try {
            return getReceiptQuery.execute(it).receipt
        } catch (_: KitchenAssistantException) {
        }
        return null
    }

}
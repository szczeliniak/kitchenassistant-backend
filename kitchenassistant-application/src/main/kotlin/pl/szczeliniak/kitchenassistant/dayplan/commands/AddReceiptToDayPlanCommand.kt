package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.DayPlanDao
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptQuery
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AddReceiptToDayPlanCommand(private val dayPlanDao: DayPlanDao, private val getReceiptQuery: GetReceiptQuery) {

    fun execute(dayPlanId: Int, receiptId: Int): SuccessResponse {
        val dayPlan = dayPlanDao.findById(dayPlanId) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        getReceiptQuery.execute(receiptId)
        dayPlan.addReceiptId(receiptId)
        return SuccessResponse(dayPlanDao.save(dayPlan).id)
    }
}
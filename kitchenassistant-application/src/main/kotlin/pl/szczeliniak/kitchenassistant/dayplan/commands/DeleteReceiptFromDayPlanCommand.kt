package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.DayPlanDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteReceiptFromDayPlanCommand(private val dayPlanDao: DayPlanDao) {

    fun execute(dayPlanId: Int, receiptId: Int): SuccessResponse {
        val dayPlan = dayPlanDao.findById(dayPlanId) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        if (!dayPlan.receiptIds.contains(receiptId)) {
            throw KitchenAssistantException(ErrorCode.RECEIPT_ID_IS_NOT_ASSIGNED_TO_DAY_PLAN)
        }
        dayPlan.receiptIds.remove(receiptId)
        return SuccessResponse(dayPlanDao.save(dayPlan).id)
    }

}
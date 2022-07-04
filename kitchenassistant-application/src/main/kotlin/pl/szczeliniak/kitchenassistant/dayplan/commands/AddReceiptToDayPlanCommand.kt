package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.DayPlanDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptFacade
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AddReceiptToDayPlanCommand(private val dayPlanDao: DayPlanDao, private val receiptFacade: ReceiptFacade) {

    fun execute(dayPlanId: Int, receiptId: Int): SuccessResponse {
        val dayPlan = dayPlanDao.findById(dayPlanId) ?: throw KitchenAssistantException(ErrorCode.DAY_PLAN_NOT_FOUND)
        receiptFacade.getReceipt(receiptId)
        dayPlan.receiptIds.add(receiptId)
        return SuccessResponse(dayPlanDao.save(dayPlan).id)
    }
}
package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.StepDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteStepCommand(private val receiptDao: ReceiptDao, private val stepDao: StepDao) {

    fun execute(receiptId: Int, stepId: Int): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)
        val step = receipt.deleteStepById(stepId)
        stepDao.save(step)

        return SuccessResponse(receipt.id)
    }

}
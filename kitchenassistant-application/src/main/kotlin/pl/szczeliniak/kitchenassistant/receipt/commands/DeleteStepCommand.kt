package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class DeleteStepCommand(private val receiptDao: ReceiptDao) {

    fun execute(receiptId: Int, stepId: Int): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw NotFoundException("Receipt not found")
        val step = receipt.deleteStepById(stepId)
        receiptDao.save(receipt)

        return SuccessResponse(step.id)
    }

}
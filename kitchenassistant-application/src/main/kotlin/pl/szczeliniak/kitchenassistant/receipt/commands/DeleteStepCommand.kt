package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao

class DeleteStepCommand(private val receiptDao: ReceiptDao) {

    fun execute(receiptId: Int, stepId: Int): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw NotFoundException("Receipt not found")
        val step = receipt.deleteStepById(stepId)
        receiptDao.save(receipt)

        return SuccessResponse(step.id)
    }

}
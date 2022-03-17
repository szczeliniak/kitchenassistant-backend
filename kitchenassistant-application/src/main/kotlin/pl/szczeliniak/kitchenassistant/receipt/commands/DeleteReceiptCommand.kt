package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao

class DeleteReceiptCommand(private val receiptDao: ReceiptDao) {

    fun execute(id: Int): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")
        receipt.markAsDeleted()

        return SuccessResponse(receiptDao.save(receipt).id)
    }

}
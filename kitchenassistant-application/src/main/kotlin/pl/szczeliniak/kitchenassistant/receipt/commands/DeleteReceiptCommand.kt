package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class DeleteReceiptCommand(private val receiptDao: ReceiptDao) {

    fun execute(id: Int): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")
        receipt.markAsDeleted()

        return SuccessResponse(receiptDao.save(receipt).id)
    }

}
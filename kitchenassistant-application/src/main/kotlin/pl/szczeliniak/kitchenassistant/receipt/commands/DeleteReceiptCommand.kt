package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteReceiptCommand(private val receiptDao: ReceiptDao) {

    fun execute(id: Int): SuccessResponse {
        val receipt =
            receiptDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)
        receipt.markAsDeleted()

        return SuccessResponse(receiptDao.save(receipt).id)
    }

}
package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AddNewReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.ReceiptFactory

class AddNewReceipt(private val receiptDao: ReceiptDao, private val receiptFactory: ReceiptFactory) {

    fun execute(dto: AddNewReceiptDto): SuccessResponse {
        receiptDao.save(receiptFactory.create(dto))
        return SuccessResponse()
    }

}
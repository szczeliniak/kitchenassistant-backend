package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.ReceiptFactory

class AddReceiptCommand(private val receiptDao: ReceiptDao, private val receiptFactory: ReceiptFactory) {

    fun execute(dto: NewReceiptDto): SuccessResponse {
        receiptDao.save(receiptFactory.create(dto))
        return SuccessResponse()
    }

}
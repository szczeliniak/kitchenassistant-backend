package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateReceiptDto

class UpdateReceiptCommand(private val receiptDao: ReceiptDao) {

    fun execute(id: Int, dto: UpdateReceiptDto): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")

        receipt.update(dto.name, dto.description, dto.userId, dto.author, dto.source)

        return SuccessResponse(receiptDao.save(receipt).id)
    }

}
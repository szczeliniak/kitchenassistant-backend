package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao

class DeleteReceiptPhotoCommand(
    private val receiptDao: ReceiptDao
) {

    fun execute(id: Int, name: String): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")
        receipt.deletePhotoByName(name)
        receiptDao.save(receipt)
        return SuccessResponse(receipt.id)
    }

}
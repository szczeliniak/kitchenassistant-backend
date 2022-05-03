package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class DivestReceiptPhotoCommand(
    private val receiptDao: ReceiptDao
) {

    fun execute(receiptId: Int, photoId: Int): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw NotFoundException("Receipt not found")
        receipt.deletePhotoById(photoId)
        receiptDao.save(receipt)
        return SuccessResponse(receipt.id)
    }

}
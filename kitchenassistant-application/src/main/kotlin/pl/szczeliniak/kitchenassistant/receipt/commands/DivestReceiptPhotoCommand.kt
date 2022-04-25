package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class DivestReceiptPhotoCommand(
    private val receiptDao: ReceiptDao,
    private val photoDao: PhotoDao
) {

    fun execute(receiptId: Int, photoId: Int): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw NotFoundException("Receipt not found")
        val photo = receipt.getPhotoById(photoId) ?: throw NotFoundException("Photo not found")
        photo.markAsDeleted()
        photoDao.save(photo)
        return SuccessResponse(receipt.id)
    }

}
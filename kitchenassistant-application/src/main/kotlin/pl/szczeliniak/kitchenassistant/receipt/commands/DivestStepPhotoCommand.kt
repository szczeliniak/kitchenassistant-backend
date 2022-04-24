package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao

class DivestStepPhotoCommand(
    private val receiptDao: ReceiptDao,
    private val photoDao: PhotoDao
) {

    fun execute(receiptId: Int, stepId: Int, photoId: Int): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw NotFoundException("Receipt not found")
        val step = receipt.getStepById(stepId) ?: throw NotFoundException("Step not found")
        val photo = step.getPhotoById(photoId) ?: throw NotFoundException("Photo not found")
        photo.markAsDeleted()
        photoDao.save(photo)
        return SuccessResponse(receipt.id)
    }

}
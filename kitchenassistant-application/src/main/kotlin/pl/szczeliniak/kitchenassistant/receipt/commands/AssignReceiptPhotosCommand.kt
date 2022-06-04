package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AssignPhotosToReceiptDto
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class AssignReceiptPhotosCommand(
    private val receiptDao: ReceiptDao,
    private val photoDao: PhotoDao
) {

    fun execute(id: Int, request: AssignPhotosToReceiptDto): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")

        request.ids.forEach {
            if (receipt.hasPhotoWithId(it)) {
                return@forEach
            }
            receipt.addPhoto(photoDao.findById(it) ?: throw NotFoundException("Photo with id $it not found"))
        }

        return SuccessResponse(receiptDao.save(receipt).id)
    }

}
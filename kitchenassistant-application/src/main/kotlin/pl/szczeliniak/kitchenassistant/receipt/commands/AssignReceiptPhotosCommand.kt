package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AssignPhotosToReceiptDto
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AssignReceiptPhotosCommand(
    private val receiptDao: ReceiptDao,
    private val photoDao: PhotoDao
) {

    fun execute(id: Int, request: AssignPhotosToReceiptDto): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)

        request.ids.forEach {
            if (receipt.photos.any { photo -> photo.id == it }) {
                return@forEach
            }
            receipt.photos.add(photoDao.findById(it) ?: throw KitchenAssistantException(ErrorCode.PHOTO_NOT_FOUND))
        }

        return SuccessResponse(receiptDao.save(receipt).id)
    }

}
package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AddReceiptPhotosDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.PhotoFactory

class AddReceiptPhotosCommand(
    private val receiptDao: ReceiptDao,
    private val photoDao: PhotoDao,
    private val photoFactory: PhotoFactory
) {

    fun execute(id: Int, request: AddReceiptPhotosDto): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")
        request.names.filter { receipt.getPhotoByName(it) == null }
            .forEach {
                val photo = photoFactory.create(it)
                photoDao.save(photo)
                receipt.addPhoto(photo)
            }
        return SuccessResponse(receiptDao.save(receipt).id)
    }

}
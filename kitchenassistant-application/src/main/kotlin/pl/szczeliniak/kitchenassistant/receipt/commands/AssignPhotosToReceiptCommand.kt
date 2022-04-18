package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.FileDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AssignPhotosToReceiptDto

class AssignPhotosToReceiptCommand(
    private val receiptDao: ReceiptDao,
    private val fileDao: FileDao
) {

    fun execute(id: Int, request: AssignPhotosToReceiptDto): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")
        request.names.filter { receipt.getPhotoById(it) == null }
            .forEach {
                receipt.addPhoto(fileDao.findById(it) ?: throw NotFoundException("File not found"))
            }
        return SuccessResponse(receiptDao.save(receipt).id)
    }

}
package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.file.queries.CheckIfFileExistsQuery
import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AssignFilesAsReceiptPhotosDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.PhotoFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class AssignReceiptPhotosCommand(
    private val receiptDao: ReceiptDao,
    private val checkIfFileExistsQuery: CheckIfFileExistsQuery,
    private val photoFactory: PhotoFactory,
    private val photoDao: PhotoDao
) {

    fun execute(id: Int, request: AssignFilesAsReceiptPhotosDto): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")
        request.fileIds.forEach {
            if (!checkIfFileExistsQuery.execute(it).exists) {
                throw NotFoundException("File not found")
            }
        }
        request.fileIds.forEach {
            if(receipt.hasPhotoWithFileId(it)) {
                return@forEach
            }
            val photo = photoFactory.create(it)
            receipt.addPhoto(photo)
            photoDao.save(photo)
        }
        return SuccessResponse(receiptDao.save(receipt).id)
    }

}
package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.file.queries.CheckIfFileExistsQuery
import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.StepDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AssignPhotosToReceiptStepDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.PhotoFactory

class AssignStepPhotosCommand(
    private val receiptDao: ReceiptDao,
    private val stepDao: StepDao,
    private val checkIfFileExistsQuery: CheckIfFileExistsQuery,
    private val photoFactory: PhotoFactory,
    private val photoDao: PhotoDao
) {

    fun execute(id: Int, stepId: Int, request: AssignPhotosToReceiptStepDto): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")
        val step = receipt.getStepById(stepId) ?: throw NotFoundException("Step not found")

        request.fileIds.forEach {
            if (!checkIfFileExistsQuery.execute(it).exists) {
                throw NotFoundException("File not found")
            }
        }
        request.fileIds.forEach {
            if (step.photos.none { photo -> photo.id == it }) {
                val photo = photoFactory.create(it)
                step.addPhoto(photo)
                photoDao.save(photo)
            }
        }
        return SuccessResponse(stepDao.save(step).id)
    }

}
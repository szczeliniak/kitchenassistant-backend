package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.StepDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AssignPhotosToReceiptStepDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.PhotoFactory

class AssignPhotosToReceiptStepCommand(
    private val receiptDao: ReceiptDao,
    private val stepDao: StepDao,
    private val photoDao: PhotoDao,
    private val photoFactory: PhotoFactory
) {

    fun execute(id: Int, stepId: Int, request: AssignPhotosToReceiptStepDto): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")
        val step = receipt.getStepById(stepId) ?: throw NotFoundException("Step not found")
        request.names.filter { step.getPhotoByName(it) == null }
            .forEach {
                val photo = photoFactory.create(it)
                photoDao.save(photo)
                step.addPhoto(photo)
            }
        return SuccessResponse(stepDao.save(step).id)
    }

}
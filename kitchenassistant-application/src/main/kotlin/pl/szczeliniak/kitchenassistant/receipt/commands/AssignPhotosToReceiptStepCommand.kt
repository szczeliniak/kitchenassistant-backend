package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.FileDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.StepDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AssignPhotosToReceiptStepDto

class AssignPhotosToReceiptStepCommand(
    private val receiptDao: ReceiptDao,
    private val stepDao: StepDao,
    private val fileDao: FileDao
) {

    fun execute(id: Int, stepId: Int, request: AssignPhotosToReceiptStepDto): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")
        val step = receipt.getStepById(stepId) ?: throw NotFoundException("Step not found")
        request.names.filter { step.getPhotoById(it) == null }
            .forEach {
                step.addPhoto(fileDao.findById(it) ?: throw NotFoundException("File not found"))
            }
        return SuccessResponse(stepDao.save(step).id)
    }

}
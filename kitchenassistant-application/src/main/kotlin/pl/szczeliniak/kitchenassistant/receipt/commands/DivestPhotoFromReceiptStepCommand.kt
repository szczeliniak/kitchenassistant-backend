package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.StepDao

class DivestPhotoFromReceiptStepCommand(
    private val receiptDao: ReceiptDao,
    private val stepDao: StepDao
) {

    fun execute(id: Int, stepId: Int, name: String): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")
        val step = receipt.getStepById(stepId) ?: throw NotFoundException("Step not found")
        step.deletePhotoByName(name)
        return SuccessResponse(stepDao.save(step).id)
    }

}
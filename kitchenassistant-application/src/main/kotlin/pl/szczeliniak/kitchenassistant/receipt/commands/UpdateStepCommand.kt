package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.StepDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateStepDto
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class UpdateStepCommand(private val receiptDao: ReceiptDao, private val stepDao: StepDao) {

    fun execute(receiptId: Int, stepId: Int, dto: UpdateStepDto): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw NotFoundException("Receipt not found")

        val step = receipt.steps.firstOrNull { it.id == stepId } ?: throw NotFoundException("Step not found")

        step.update(dto.name, dto.description, dto.sequence)

        return SuccessResponse(stepDao.save(step).id)
    }

}
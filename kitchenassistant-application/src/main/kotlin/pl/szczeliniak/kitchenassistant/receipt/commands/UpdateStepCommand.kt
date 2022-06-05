package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.StepDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateStepDto
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class UpdateStepCommand(private val receiptDao: ReceiptDao, private val stepDao: StepDao) {

    fun execute(receiptId: Int, stepId: Int, dto: UpdateStepDto): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)

        val step =
            receipt.steps.firstOrNull { it.id == stepId } ?: throw KitchenAssistantException(ErrorCode.STEP_NOT_FOUND)

        step.update(dto.name, dto.description, dto.sequence)

        return SuccessResponse(stepDao.save(step).id)
    }

}
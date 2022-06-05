package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.StepDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.StepFactory
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AddStepCommand(
    private val receiptDao: ReceiptDao,
    private val stepDao: StepDao,
    private val stepFactory: StepFactory
) {

    fun execute(receiptId: Int, dto: NewStepDto): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)
        val step = stepDao.save(stepFactory.create(dto))

        receipt.addStep(step)
        receiptDao.save(receipt)

        return SuccessResponse(step.id)
    }

}
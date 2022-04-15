package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.StepDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.StepFactory

class AddStepCommand(
    private val receiptDao: ReceiptDao,
    private val stepDao: StepDao,
    private val stepFactory: StepFactory
) {

    fun execute(receiptId: Int, dto: NewStepDto): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw NotFoundException("Receipt not found")
        val step = stepDao.save(stepFactory.create(dto))

        receipt.addStep(step)
        receiptDao.save(receipt)

        return SuccessResponse(step.id)
    }

}
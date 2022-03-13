package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.StepFactory

class AddStepCommand(private val receiptDao: ReceiptDao, private val stepFactory: StepFactory) {

    fun execute(receiptId: Int, dto: NewStepDto): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw NotFoundException("Receipt not found")
        receipt.addStep(stepFactory.create(dto))

        receiptDao.save(receipt)
        return SuccessResponse()
    }

}
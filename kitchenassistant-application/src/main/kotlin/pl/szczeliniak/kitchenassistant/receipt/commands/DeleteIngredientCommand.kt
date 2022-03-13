package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao

class DeleteIngredientCommand(private val receiptDao: ReceiptDao) {

    fun execute(receiptId: Int, ingredientId: Int): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw NotFoundException("Receipt not found")
        receipt.deleteIngredientById(ingredientId)

        receiptDao.save(receipt)
        return SuccessResponse()
    }

}
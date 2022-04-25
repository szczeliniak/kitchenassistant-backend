package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class DeleteIngredientCommand(private val receiptDao: ReceiptDao) {

    fun execute(receiptId: Int, ingredientId: Int): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw NotFoundException("Receipt not found")
        val ingredient = receipt.deleteIngredientById(ingredientId)
        receiptDao.save(receipt)

        return SuccessResponse(ingredient.id)
    }

}
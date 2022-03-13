package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao

class DeleteIngredientCommand(private val receiptDao: ReceiptDao) {

    fun execute(receiptId: Int, ingredientId: Int): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw NotFoundException("Receipt not found")

        val ingredient =
            receipt.ingredients.firstOrNull { it.id == ingredientId } ?: throw NotFoundException("Ingredient not found")
        ingredient.markAsDeleted()

        receiptDao.save(receipt)
        return SuccessResponse()
    }

}
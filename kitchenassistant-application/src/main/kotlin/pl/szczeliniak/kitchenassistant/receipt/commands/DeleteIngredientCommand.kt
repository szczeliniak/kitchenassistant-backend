package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.IngredientDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class DeleteIngredientCommand(private val receiptDao: ReceiptDao, private val ingredientDao: IngredientDao) {

    fun execute(receiptId: Int, ingredientId: Int): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw NotFoundException("Receipt not found")
        val ingredient = receipt.deleteIngredientById(ingredientId)
        ingredientDao.save(ingredient)

        return SuccessResponse(receipt.id)
    }

}
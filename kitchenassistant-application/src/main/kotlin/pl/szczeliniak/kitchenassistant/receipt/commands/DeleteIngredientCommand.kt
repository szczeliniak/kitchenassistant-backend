package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.IngredientDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteIngredientCommand(private val receiptDao: ReceiptDao, private val ingredientDao: IngredientDao) {

    fun execute(receiptId: Int, ingredientId: Int): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)
        val ingredient = receipt.deleteIngredientById(ingredientId)
        ingredientDao.save(ingredient)

        return SuccessResponse(receipt.id)
    }

}
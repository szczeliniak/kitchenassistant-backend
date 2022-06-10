package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteIngredientCommand(private val receiptDao: ReceiptDao) {

    fun execute(receiptId: Int, ingredientGroupId: Int, ingredientId: Int): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)
        val ingredientGroup = receipt.getIngredientGroupById(ingredientGroupId)
            ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_GROUP_NOT_FOUND)
        val ingredient = ingredientGroup.deleteIngredientById(ingredientId)
        receiptDao.save(receipt)
        return SuccessResponse(ingredient.id)
    }

}
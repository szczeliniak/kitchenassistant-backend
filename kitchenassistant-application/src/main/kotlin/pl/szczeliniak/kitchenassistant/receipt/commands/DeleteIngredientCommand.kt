package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteIngredientCommand(private val receiptDao: ReceiptDao) {

    fun execute(receiptId: Int, ingredientGroupId: Int, ingredientId: Int): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)
        val ingredientGroup = receipt.ingredientGroups.firstOrNull { ingredientGroupId == it.id }
            ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_GROUP_NOT_FOUND)

        val ingredient =
            ingredientGroup.ingredients.firstOrNull { it.id == ingredientId } ?: throw KitchenAssistantException(
                ErrorCode.INGREDIENT_NOT_FOUND
            )

        if (ingredient.deleted) {
            throw KitchenAssistantException(ErrorCode.INGREDIENT_ALREADY_REMOVED)
        }

        ingredient.deleted = true

        receiptDao.save(receipt)
        return SuccessResponse(ingredientId)
    }

}
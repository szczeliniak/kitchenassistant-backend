package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateIngredientDto
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class UpdateIngredientCommand(private val receiptDao: ReceiptDao) {

    fun execute(receiptId: Int, ingredientGroupId: Int, ingredientId: Int, dto: UpdateIngredientDto): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)
        val ingredientGroup = receipt.getIngredientGroupById(ingredientGroupId)
            ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_GROUP_NOT_FOUND)
        val ingredient = ingredientGroup.getIngredientById(ingredientId)
            ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_NOT_FOUND)
        ingredient.update(dto.name, dto.quantity)

        receiptDao.save(receipt)

        return SuccessResponse(ingredient.id)
    }

}
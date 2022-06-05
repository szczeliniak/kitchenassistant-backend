package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.IngredientDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateIngredientDto
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class UpdateIngredientCommand(private val receiptDao: ReceiptDao, private val ingredientDao: IngredientDao) {

    fun execute(receiptId: Int, ingredientId: Int, dto: UpdateIngredientDto): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)

        val ingredient =
            receipt.ingredients.firstOrNull { it.id == ingredientId }
                ?: throw KitchenAssistantException(ErrorCode.INGREDIENT_NOT_FOUND)

        ingredient.update(dto.name, dto.quantity)

        return SuccessResponse(ingredientDao.save(ingredient).id)
    }

}
package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.IngredientDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.IngredientFactory
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AddIngredientCommand(
    private val receiptDao: ReceiptDao,
    private val ingredientDao: IngredientDao,
    private val ingredientFactory: IngredientFactory
) {

    fun execute(receiptId: Int, dto: NewIngredientDto): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)
        val ingredient = ingredientDao.save(ingredientFactory.create(dto))

        receipt.addIngredient(ingredient)
        receiptDao.save(receipt)

        return SuccessResponse(ingredient.id)
    }

}
package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.IngredientFactory

class AddIngredientCommand(private val receiptDao: ReceiptDao, private val ingredientFactory: IngredientFactory) {

    fun execute(receiptId: Int, dto: NewIngredientDto): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw NotFoundException("Receipt not found")
        receipt.addIngredient(ingredientFactory.create(dto))

        receiptDao.save(receipt)
        return SuccessResponse()
    }

}
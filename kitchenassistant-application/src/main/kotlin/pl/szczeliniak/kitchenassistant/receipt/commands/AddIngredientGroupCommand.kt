package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.IngredientGroupDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientGroupDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.IngredientGroupFactory
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AddIngredientGroupCommand(
    private val receiptDao: ReceiptDao,
    private val ingredientGroupFactory: IngredientGroupFactory,
    private val ingredientGroupDao: IngredientGroupDao
) {

    fun execute(receiptId: Int, dto: NewIngredientGroupDto): SuccessResponse {
        val receipt = receiptDao.findById(receiptId) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)
        val ingredientGroup = ingredientGroupDao.save(ingredientGroupFactory.create(dto))
        receipt.ingredientGroups.add(ingredientGroup)
        receiptDao.save(receipt)
        return SuccessResponse(ingredientGroup.id)
    }

}
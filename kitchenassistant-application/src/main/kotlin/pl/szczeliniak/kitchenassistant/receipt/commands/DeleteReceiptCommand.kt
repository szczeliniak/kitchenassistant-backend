package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dayplan.commands.DeassignReceiptsFromDayPlansCommand
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.DeassignReceiptFromShoppingListsCommand

class DeleteReceiptCommand(
    private val receiptDao: ReceiptDao,
    private val deassignReceiptsFromDayPlansCommand: DeassignReceiptsFromDayPlansCommand,
    private val deassignReceiptFromShoppingListsCommand: DeassignReceiptFromShoppingListsCommand
) {

    fun execute(id: Int): SuccessResponse {
        val receipt =
            receiptDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)
        if (receipt.deleted) {
            throw KitchenAssistantException(ErrorCode.RECEIPT_ALREADY_REMOVED)
        }
        receipt.deleted = true

        deassignReceiptsFromDayPlansCommand.execute(id)
        deassignReceiptFromShoppingListsCommand.execute(id)

        return SuccessResponse(receiptDao.save(receipt).id)
    }

}
package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptResponse
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException

open class GetReceiptQuery(private val receiptDao: ReceiptDao, private val receiptConverter: ReceiptConverter) {

    open fun execute(id: Int): ReceiptResponse {
        return ReceiptResponse(
            receiptConverter.mapDetails(
                receiptDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)
            )
        )
    }

}
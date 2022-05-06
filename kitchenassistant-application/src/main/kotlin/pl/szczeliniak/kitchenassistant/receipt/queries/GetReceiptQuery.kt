package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

open class GetReceiptQuery(private val receiptDao: ReceiptDao, private val receiptConverter: ReceiptConverter) {

    open fun execute(id: Int): ReceiptResponse {
        return ReceiptResponse(
            receiptConverter.map(receiptDao.findById(id) ?: throw NotFoundException("Receipt not found"))
        )
    }

}
package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

open class GetReceiptQuery(private val receiptDao: ReceiptDao) {

    open fun execute(id: Int): ReceiptResponse {
        return ReceiptResponse(
            ReceiptDto.fromDomain(
                receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")
            )
        )
    }

}
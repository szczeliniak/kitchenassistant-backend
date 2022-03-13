package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.ReceiptCriteria
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptsResponse

class GetReceiptsQuery(private val receiptDao: ReceiptDao) {

    fun execute(criteria: ReceiptCriteria): ReceiptsResponse {
        return ReceiptsResponse(receiptDao.findAll(criteria).map { ReceiptDto.fromDomain(it) })
    }

}
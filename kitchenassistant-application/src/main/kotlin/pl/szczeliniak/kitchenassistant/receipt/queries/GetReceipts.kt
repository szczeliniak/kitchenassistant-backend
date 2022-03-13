package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptCriteriaDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptsResponse

class GetReceipts(private val receiptDao: ReceiptDao) {

    fun execute(criteria: ReceiptCriteriaDto): ReceiptsResponse {
        return ReceiptsResponse(receiptDao.findAll(criteria).map { ReceiptDto.fromDomain(it) })
    }

}
package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.ReceiptCriteria
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptsResponse
import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination
import pl.szczeliniak.kitchenassistant.shared.dtos.PaginationUtils

class GetReceiptsQuery(private val receiptDao: ReceiptDao, private val receiptConverter: ReceiptConverter) {

    fun execute(page: Long?, limit: Int?, criteria: ReceiptCriteria): ReceiptsResponse {
        val currentPage = PaginationUtils.calculatePageNumber(page)
        val currentLimit = PaginationUtils.calculateLimit(limit)
        val offset = PaginationUtils.calculateOffset(currentPage, currentLimit)
        val totalNumberOfPages = PaginationUtils.calculateNumberOfPages(currentLimit, receiptDao.count(criteria))
        return ReceiptsResponse(
            receiptDao.findAll(criteria, offset, currentLimit).map { receiptConverter.map(it) },
            Pagination(currentPage, currentLimit, totalNumberOfPages)
        )
    }

}
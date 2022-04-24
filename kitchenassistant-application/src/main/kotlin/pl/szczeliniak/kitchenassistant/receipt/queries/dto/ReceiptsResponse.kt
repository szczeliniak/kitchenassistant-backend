package pl.szczeliniak.kitchenassistant.receipt.queries.dto

import pl.szczeliniak.kitchenassistant.common.dto.Pagination

data class ReceiptsResponse(
    val receipts: List<ReceiptDto>,
    val pagination: Pagination
)
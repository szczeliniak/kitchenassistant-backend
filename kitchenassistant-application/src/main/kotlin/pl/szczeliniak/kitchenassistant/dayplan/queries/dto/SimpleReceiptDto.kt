package pl.szczeliniak.kitchenassistant.dayplan.queries.dto

data class SimpleReceiptDto(
    val id: Int,
    val name: String,
    val author: String?,
    val category: String?
)
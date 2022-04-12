package pl.szczeliniak.kitchenassistant.receipt

data class ReceiptCriteria(
    val userId: Int?,
    val categoryId: Int?,
    val name: String?
)
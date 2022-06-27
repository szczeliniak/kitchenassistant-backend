package pl.szczeliniak.kitchenassistant.receipt

data class ReceiptCriteria(
    val userId: Int? = null,
    val categoryId: Int? = null,
    val name: String? = null,
    val tag: String? = null
)
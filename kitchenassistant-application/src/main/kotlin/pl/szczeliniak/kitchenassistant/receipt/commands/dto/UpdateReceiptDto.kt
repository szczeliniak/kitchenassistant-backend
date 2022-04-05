package pl.szczeliniak.kitchenassistant.receipt.commands.dto

data class UpdateReceiptDto(
    var userId: Int = 0,
    var name: String = "",
    var categoryId: Int? = null,
    var description: String? = null,
    var author: String? = null,
    var source: String? = null
)
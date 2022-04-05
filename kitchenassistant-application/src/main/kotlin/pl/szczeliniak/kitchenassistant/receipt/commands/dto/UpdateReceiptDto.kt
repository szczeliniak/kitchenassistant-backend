package pl.szczeliniak.kitchenassistant.receipt.commands.dto

data class UpdateReceiptDto(
    var userId: Int = 0,
    var name: String = "",
    var description: String = "",
    var author: String = "",
    var source: String = ""
)
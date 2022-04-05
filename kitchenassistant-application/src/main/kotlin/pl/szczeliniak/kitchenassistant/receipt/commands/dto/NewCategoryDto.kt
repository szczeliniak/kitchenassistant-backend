package pl.szczeliniak.kitchenassistant.receipt.commands.dto

data class NewCategoryDto(
    var name: String = "",
    var userId: Int = 0
)
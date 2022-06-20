package pl.szczeliniak.kitchenassistant.receipt.queries.dto

data class CategoryDto(
    val id: Int,
    val name: String,
    var sequence: Int?
)
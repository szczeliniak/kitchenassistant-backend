package pl.szczeliniak.kitchenassistant.receipt.queries.dto

data class StepDto(
    val id: Int,
    val name: String,
    val description: String?,
    val sequence: Int?
)
package pl.szczeliniak.kitchenassistant.receipt.commands.dto

data class NewStepDto(
    var title: String = "",
    var description: String? = null,
    var sequence: Int? = null
)
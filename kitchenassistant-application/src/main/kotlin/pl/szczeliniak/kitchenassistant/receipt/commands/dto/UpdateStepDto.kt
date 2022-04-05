package pl.szczeliniak.kitchenassistant.receipt.commands.dto

data class UpdateStepDto(
    var name: String = "",
    var description: String? = null,
    var sequence: Int? = null
)
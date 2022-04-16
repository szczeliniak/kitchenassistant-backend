package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import org.hibernate.validator.constraints.Length

data class NewStepDto(
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Length(max = 1000) var description: String? = null,
    var sequence: Int? = null
)
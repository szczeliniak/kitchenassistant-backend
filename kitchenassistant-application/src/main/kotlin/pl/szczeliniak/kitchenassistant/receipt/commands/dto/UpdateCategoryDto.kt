package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import org.hibernate.validator.constraints.Length

data class UpdateCategoryDto(
    @field:Length(min = 1, max = 100) var name: String = "",
    var sequence: Int? = null
)
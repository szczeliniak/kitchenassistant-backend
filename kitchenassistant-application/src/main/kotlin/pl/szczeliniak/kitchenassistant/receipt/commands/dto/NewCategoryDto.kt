package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Min

data class NewCategoryDto(
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Min(1) var userId: Int = 0,
    var sequence: Int? = null
)
package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import org.hibernate.validator.constraints.Length

data class UpdateReceiptDto(
    @field:Length(min = 1, max = 1000) var name: String = "",
    var categoryId: Int? = null,
    @field:Length(max = 1000) var description: String? = null,
    @field:Length(max = 50) var author: String? = null,
    @field:Length(max = 100) var source: String? = null
)
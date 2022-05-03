package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UpdateReceiptDto(
    @field:Length(min = 1, max = 1000) var name: String = "",
    var categoryId: Int? = null,
    @field:Length(max = 1000) var description: String? = null,
    @field:Length(max = 50) var author: String? = null,
    @field:Length(max = 100) var source: String? = null,
    @field:Size(min = 0, max = 30) var tags: Set<@NotNull String> = setOf(),
    @field:Size(min = 0, max = 30) var photos: Set<@NotNull Int> = setOf()
)
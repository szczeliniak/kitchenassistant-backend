package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class NewStepDto(
    @field:Length(min = 1, max = 100) var name: String = "",
    @field:Length(max = 1000) var description: String? = null,
    var sequence: Int? = null,
    @field:Size(min = 0, max = 20) var photos: List<@NotNull @Length(min = 1, max = 100) String> = listOf()
)
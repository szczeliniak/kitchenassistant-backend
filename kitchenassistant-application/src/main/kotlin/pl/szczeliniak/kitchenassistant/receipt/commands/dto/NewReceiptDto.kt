package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import org.hibernate.validator.constraints.Length
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class NewReceiptDto(
    @field:Min(1) var userId: Int = 0,
    @field:Length(min = 1, max = 100) var name: String = "",
    var categoryId: Int? = null,
    @field:Length(max = 1000) var description: String? = null,
    @field:Length(max = 50) var author: String? = null,
    @field:Length(max = 100) var source: String? = null,
    @field:Size(min = 0, max = 30) var ingredients: List<@Valid NewIngredientDto> = listOf(),
    @field:Size(min = 0, max = 30) var steps: List<@Valid NewStepDto> = listOf(),
    @field:Size(min = 0, max = 30) var photos: List<@NotBlank String> = listOf()
)